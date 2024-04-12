﻿using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using WebStore.Constants;
using WebStore.Data.Entities;
using WebStore.Data.Entities.Identity;

namespace WebStore.Data
{
    public static class SeederDB
    {
        public static void SeedData(this IApplicationBuilder app)
        {
            using (var scope = app.ApplicationServices.GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<MyAppContext>();
                context.Database.Migrate();

                if(!context.Categories.Any())
                {
                    var kovbasy = new CategoryEntity
                    {
                        Name = "Ковбаси",
                        Description = "Хороші і довгі ковбаси",
                        Image = "kovbasa.jpg"
                    };
                    var vsutiy = new CategoryEntity
                    {
                        Name = "Взуття",
                        Description = "Гарне взуття із гарнатією 5 років." +
                        "Можна нирять під воду.",
                        Image = "shoes.jpg"
                    };
                    context.Categories.Add(kovbasy);
                    context.Categories.Add(vsutiy);
                    context.SaveChanges();
                }

                var userManager = scope.ServiceProvider
    .GetRequiredService<UserManager<UserEntity>>();

                var roleManager = scope.ServiceProvider
                    .GetRequiredService<RoleManager<RoleEntity>>();

                #region Seed Roles and Users

                if (!context.Roles.Any())
                {
                    foreach (var role in Roles.All)
                    {
                        var result = roleManager.CreateAsync(new RoleEntity
                        {
                            Name = role
                        }).Result;
                    }
                }

                if (!context.Users.Any())
                {
                    UserEntity user = new()
                    {
                        FirstName = "Іван",
                        LastName = "Капот",
                        Email = "admin@gmail.com",
                        UserName = "admin@gmail.com",
                    };
                    var result = userManager.CreateAsync(user, "123456")
                        .Result;    
                    if (result.Succeeded)
                    {
                        result = userManager
                            .AddToRoleAsync(user, Roles.Admin)
                            .Result;
                    }
                }

                #endregion
            }
        }
    }
}
