using API_ANDROID.Model;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace API_ANDROID.Controllers
{
    [Route("api/product")]
    [ApiController]
    public class ProductController : ControllerBase
    {

        private readonly IConfiguration _configuration;
        public ProductController(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        [HttpGet("getAllProduct")]
        public async Task<IActionResult> GetAllProducts()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT p.id AS Product_id,p.name AS Product_name, p.price AS price, p.image AS image, m.name AS Manufacturer_name ,
                       AVG(c.star) AS average_rating, count(c.id) as numComment
                FROM [salephone].[product] p
                LEFT JOIN [salephone].[manufacturer] m ON p.manufacturer_id = m.id
                LEFT JOIN [salephone].[comment] c ON p.id = c.product_id
                GROUP BY p.id, p.name, p.price, p.image, m.name;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var products = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var product = new
                                {
                                    product_id = reader.GetInt32(reader.GetOrdinal("Product_id")),
                                    product_name = reader.GetString(reader.GetOrdinal("Product_name")),
                                    price = reader.GetDecimal(reader.GetOrdinal("price")),
                                    image = reader.GetString(reader.GetOrdinal("image")),
                                    manufacturer_name = reader.GetString(reader.GetOrdinal("Manufacturer_name")),
                                    numComment = reader.GetInt32(reader.GetOrdinal("numComment")),
                                    averageRating = reader.IsDBNull(reader.GetOrdinal("average_rating"))
                                        ? (int?)0
                                        : reader.GetInt32(reader.GetOrdinal("average_rating"))
                                    

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

        [HttpGet("GetProductInfoById/{productId}")]
        public async Task<IActionResult> GetProductInfoById(int productId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT p.id AS Product_id,p.name AS Product_name, p.price AS price, p.image AS image, p.manufacturer_id,
                       AVG(c.star) AS average_rating, p.[size] as size, p.[weight] as weight, p.[chipset] as chipset, p.[ram] as ram, 
                        p.[storage] as storage, p.[battery] as battery, p.[charging] as charging, p.[video] as video, count(c.id) as numComment
                FROM [salephone].[product] p
                LEFT JOIN [salephone].[comment] c ON p.id = c.product_id
                where p.id = @productId
                GROUP BY p.id, p.name, p.price, p.image, p.manufacturer_id, p.[size], p.[weight], p.[chipset], p.[ram],p.[storage],p.[battery],p.[charging],p.[video];
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@productId", productId);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {

                            if (await reader.ReadAsync())
                            {
                                var product = new
                                {
                                    product_id = reader.GetInt32(reader.GetOrdinal("Product_id")),
                                    product_name = reader.GetString(reader.GetOrdinal("Product_name")),
                                    price = reader.GetDecimal(reader.GetOrdinal("price")),
                                    image = reader.GetString(reader.GetOrdinal("image")),
                                    manufacturer_id = reader.GetInt32(reader.GetOrdinal("manufacturer_id")),
                                    averageRating = reader.IsDBNull(reader.GetOrdinal("average_rating")) ? (int?)0 : reader.GetInt32(reader.GetOrdinal("average_rating")),
                                    numComment = reader.GetInt32(reader.GetOrdinal("numComment")),
                                    size = reader.IsDBNull(reader.GetOrdinal("size")) ? "" : reader.GetString(reader.GetOrdinal("size")),
                                    weight = reader.IsDBNull(reader.GetOrdinal("weight")) ? "" : reader.GetString(reader.GetOrdinal("weight")),
                                    chipset = reader.IsDBNull(reader.GetOrdinal("chipset")) ? "" : reader.GetString(reader.GetOrdinal("chipset")),
                                    ram = reader.IsDBNull(reader.GetOrdinal("ram")) ? "" : reader.GetString(reader.GetOrdinal("ram")),
                                    storage = reader.IsDBNull(reader.GetOrdinal("storage")) ? "" : reader.GetString(reader.GetOrdinal("storage")),
                                    battery = reader.IsDBNull(reader.GetOrdinal("battery")) ? "" : reader.GetString(reader.GetOrdinal("battery")),
                                    charging = reader.IsDBNull(reader.GetOrdinal("charging")) ? "" : reader.GetString(reader.GetOrdinal("charging")),
                                    video = reader.IsDBNull(reader.GetOrdinal("video")) ? "" : reader.GetString(reader.GetOrdinal("video"))
                                };

                                return Ok(product);
                            }
                            else
                            {
                                return Ok(new { result = "Product not found" });
                            }

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

        [HttpPut("addProduct")]
        public async Task<IActionResult> AddProduct([FromBody] AddProductModel model)
        {
            if (model == null)
            {
                return BadRequest("Invalid data.");
            }

            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                        INSERT INTO [salephone].[product] (name, price, image, manufacturer_id, num, [size] ,[weight] ,[chipset] ,[ram] ,[storage] ,[battery] ,[charging] ,[video])
                        VALUES (@name, @price, @image, @manufacturerId, @num, @size, @weight, @chipset, @ram, @storage, @battery, @charging, @video);
                    ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@name", model.name);
                        command.Parameters.AddWithValue("@price", model.price);
                        command.Parameters.AddWithValue("@image", model.image);
                        command.Parameters.AddWithValue("@manufacturerId", model.manufacturerId);
                        command.Parameters.AddWithValue("@num", model.num);
                        command.Parameters.AddWithValue("@size", model.size);
                        command.Parameters.AddWithValue("@weight", model.weight);
                        command.Parameters.AddWithValue("@chipset", model.chipset);
                        command.Parameters.AddWithValue("@ram", model.ram);
                        command.Parameters.AddWithValue("@storage", model.storage);
                        command.Parameters.AddWithValue("@battery", model.battery);
                        command.Parameters.AddWithValue("@charging", model.charging);
                        command.Parameters.AddWithValue("@video", model.video);

                        int rowsAffected = await command.ExecuteNonQueryAsync();

                        if (rowsAffected > 0)
                        {
                            return Ok(new { result = "Add successful" });
                        }
                        else
                        {
                            return Ok(new { result = "Add failed" });

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

        [HttpGet("getNumberProductInInventory")]
        public async Task<IActionResult> GetNumberProductInInventory()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT p.id AS Product_id,p.name AS name, p.num AS num
                FROM [salephone].[product] p
                WHERE p.num <> 0;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var products = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var product = new
                                {
                                    product_id = reader.GetInt32(reader.GetOrdinal("Product_id")),
                                    name = reader.GetString(reader.GetOrdinal("name")),
                                    num = reader.GetInt32(reader.GetOrdinal("num"))
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

        [HttpPut("updateProductNumById/{productId}")]
        public async Task<IActionResult> UpdateProductNumById(int productId, [FromBody] UpdateProductModel model)
        {
            if (model == null)
            {
                return BadRequest("Invalid data.");
            }

            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                UPDATE [salephone].[product]
                SET num = @num
                WHERE id = @productId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@productId", productId);
                        command.Parameters.AddWithValue("@num", model.num);

                        int rowsAffected = await command.ExecuteNonQueryAsync();

                        if (rowsAffected > 0)
                        {
                            return Ok(new { result = "Update successful" });
                        }
                        else
                        {
                            return Ok(new { result = "Update failed" });

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

        [HttpPut("updateProductPriceById/{productId}")]
        public async Task<IActionResult> UpdateProductPriceById(int productId, [FromBody] UpdateProductModel model)
        {
            if (model == null)
            {
                return BadRequest("Invalid data.");
            }

            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                UPDATE [salephone].[product]
                SET price = @price
                WHERE id = @productId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@productId", productId);
                        command.Parameters.AddWithValue("@price", model.price);

                        int rowsAffected = await command.ExecuteNonQueryAsync();

                        if (rowsAffected > 0)
                        {
                            return Ok(new { result = "Update successful" });
                        }
                        else
                        {
                            return Ok(new { result = "Update failed" });

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

        [HttpGet("deleteProductById/{productId}")]
        public async Task<IActionResult> deletwProductById(int productId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                DELETE FROM [salephone].[product]
                WHERE id = @productId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@productId", productId);

                        int rowsAffected = await command.ExecuteNonQueryAsync();

                        if (rowsAffected > 0)
                        {
                            return Ok(new { result = "Delete successful" });
                        }
                        else
                        {
                            return Ok(new { result = "Delete failed" });

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


