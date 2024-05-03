using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace API_ANDROID.Controllers
{
    [Route("api/manufacturer")]
    [ApiController]
    public class ManufacturerController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        public ManufacturerController(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        [HttpGet("getAllManufacturer")]
        public async Task<IActionResult> GetAllManufacturers()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT *
                FROM [salephone].[manufacturer];
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var manufacturers = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var manufacturer = new
                                {
                                    id = reader.GetInt32(reader.GetOrdinal("id")),
                                    name = reader.GetString(reader.GetOrdinal("name")),
                                    image = reader.GetString(reader.GetOrdinal("image"))
                                };

                                manufacturers.Add(manufacturer);
                            }

                            return Ok(manufacturers);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }

        [HttpGet("getAllProductOfManufacturerById/{manufacturerId}")]
        public async Task<IActionResult> GetAllProductsOfManufacturerById(int manufacturerId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT name AS name, price AS price
                FROM [salephone].[product]
                WHERE manufacturer_id = @manufacturerId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@manufacturerId", manufacturerId);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var products = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var product = new
                                {
                                    name = reader.GetString(reader.GetOrdinal("name")),
                                    price = reader.GetDecimal(reader.GetOrdinal("price"))
                                };

                                products.Add(product);
                            }

                            return Ok(products);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }

    }
}
