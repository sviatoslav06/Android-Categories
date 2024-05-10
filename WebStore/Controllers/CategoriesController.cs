using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Webp;
using SixLabors.ImageSharp.Processing;
using WebStore.Data;
using WebStore.Data.Entities;
using WebStore.Data.Entities.Identity;
using WebStore.Models.Categories;

namespace WebStore.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class CategoriesController : ControllerBase
    {
        private readonly MyAppContext _appContext;
        private readonly UserManager<UserEntity> _userManager;
        private readonly IMapper _mapper;

        public CategoriesController(MyAppContext appContext, UserManager<UserEntity> userManager, IMapper mapper)
        {
            _appContext = appContext;
            _userManager = userManager;
            _mapper = mapper;
        }

        [HttpGet]
        private async Task<UserEntity> GetUserAuthAsync()
        {
            var email = User.Claims.FirstOrDefault().Value;
            var user = await _userManager.FindByEmailAsync(email);
            return user;
        }

        [HttpGet]
        public async Task<IActionResult> Get()
        {
            var user = await GetUserAuthAsync();
            var list = _appContext.Categories
                .Where(x => x.UserId == user.Id)
                .Select(x => _mapper.Map<CategoryItemViewModel>(x))
                .ToList();
            return Ok(list);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var user = await GetUserAuthAsync();
            var category = _appContext.Categories
                .Where(x => x.UserId == user.Id)
                .SingleOrDefault(x => x.Id == id);

            if (category == null)
            {
                return NotFound();
            }

            return Ok(_mapper.Map<CategoryItemViewModel>(category));
        }

        [HttpPost]
        public async Task<IActionResult> Create([FromForm] CategoryCreateViewModel model)
        {
            var user = await GetUserAuthAsync();
            var category = new CategoryEntity
            {
                Name = model.Name,
                Description = model.Description,
                UserId = user.Id,
            };

            if (model.Image != null)
            {
                using MemoryStream ms = new MemoryStream();
                await model.Image.CopyToAsync(ms);

                using Image image = Image.Load(ms.ToArray());

                image.Mutate(x =>
                {
                    x.Resize(new ResizeOptions
                    {
                        Size = new Size(1200),
                        Mode = ResizeMode.Max
                    });
                });
                string imageName = Path.GetRandomFileName() + ".webp";
                string dirSaveImage = Path.Combine(Directory.GetCurrentDirectory(), "images", imageName);

                using var stream = System.IO.File.Create(dirSaveImage);
                await image.SaveAsync(stream, new WebpEncoder());
                category.Image = imageName;
            }

            _appContext.Categories.Add(category);
            _appContext.SaveChanges();

            return Ok(_mapper.Map<CategoryItemViewModel>(category));
        }

        [HttpPut]
        public async Task<IActionResult> Edit([FromForm] CategoryEditViewModel model)
        {
            var user = await GetUserAuthAsync();
            var category = _appContext.Categories
                .Where(x => x.UserId == user.Id)
                .SingleOrDefault(x => x.Id == model.Id);

            if (category == null)
            {
                return NotFound();
            }
            if (model.Image != null)
            {
                string imgDel = category.Image;
                if (imgDel != null)
                {
                    string imgDelPath = Path.Combine(Directory.GetCurrentDirectory(), "images", imgDel);
                    if (System.IO.File.Exists(imgDelPath))
                    {
                        System.IO.File.Delete(imgDelPath);
                    }

                }
                using Image image = Image.Load(model.Image.OpenReadStream());
                image.Mutate(x =>
                {
                    x.Resize(new ResizeOptions
                    {
                        Size = new Size(1200),
                        Mode = ResizeMode.Max
                    });
                });
                string imageName = Path.GetRandomFileName() + ".webp";
                string dirSaveImage = Path.Combine(Directory.GetCurrentDirectory(), "images", imageName);

                await image.SaveAsync(dirSaveImage, new WebpEncoder());
                category.Image = imageName;
            }
            category.Description = model.Description;
            category.Name = model.Name;
            _appContext.SaveChanges();

            return Ok(_mapper.Map<CategoryItemViewModel>(category));
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var user = await GetUserAuthAsync();
            var category = _appContext.Categories
                .Where(x => x.UserId == user.Id)
                .SingleOrDefault(x => x.Id == id);

            if (category == null)
            {
                return NotFound();
            }
            string imgDel = category.Image;
            if (imgDel != null)
            {
                string imgDelPath = Path.Combine(Directory.GetCurrentDirectory(), "images", imgDel);
                if (System.IO.File.Exists(imgDelPath))
                {
                    System.IO.File.Delete(imgDelPath);
                }

            }
            _appContext.Categories.Remove(category);
            _appContext.SaveChanges();
            return Ok();
        }
    }
}
