using AutoMapper;
using WebStore.Data.Entities;
using WebStore.Models.Categories;

namespace WebStore.Mapper
{
    public class AppMapProfile : Profile
    {
        public AppMapProfile()
        {
            CreateMap<CategoryEntity, CategoryItemViewModel>();
        }
    }
}