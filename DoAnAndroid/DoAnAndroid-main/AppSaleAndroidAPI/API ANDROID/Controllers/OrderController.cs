using API_ANDROID.Model;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace API_ANDROID.Controllers
{
    [Route("api/order")]
    [ApiController]
    public class OrderController : ControllerBase
    {
        private readonly IConfiguration _configuration;

        public OrderController(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        [HttpPut("addOrder")]
        public async Task<IActionResult> AddOrder([FromBody] AddOrderModel model)
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
                        INSERT INTO [salephone].[order] (user_id, totalprice, created_date, address)
                        VALUES (@userId, @totalPrice, GETDATE(), @address);
                    ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@userId", model.userId);
                        command.Parameters.AddWithValue("@totalPrice", model.totalPrice);
                        command.Parameters.AddWithValue("@address", model.address);

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

        [HttpPut("addOrderDetail")]
        public async Task<IActionResult> AddOrderDetail([FromBody] AddOrderDetailModel model)
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
                        INSERT INTO [salephone].[order_detail] (order_id, product_id, num)
                        VALUES (@orderId, @productId, @num);
                    ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@orderId", model.orderId);
                        command.Parameters.AddWithValue("@productId", model.productId);
                        command.Parameters.AddWithValue("@num", model.num);

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

        [HttpGet("getAllOrderOfUserByUserId/{userId}")]
        public async Task<IActionResult> GetAllOrderOfUserByUserId(int userId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT SUM(totalprice) AS TotalPrice, created_date, id
                FROM [salephone].[order]
                WHERE user_id = @userId
                GROUP BY created_date, id;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@userId", userId);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var orders = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var order = new
                                {
                                    order_id = reader.GetInt32(reader.GetOrdinal("id")),
                                    total_price = reader.GetDecimal(reader.GetOrdinal("TotalPrice")),
                                    created_date = reader.GetDateTime(reader.GetOrdinal("created_date"))
                                };

                                orders.Add(order);
                            }

                            return Ok(orders);
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

        [HttpGet("getAllProductInOrderById/{orderId}")]
        public async Task<IActionResult> GetAllProductInOrderById(int orderId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT p.name, od.num AS quantity, (p.price * od.num) AS total_price
                FROM [salephone].[order_detail] od
                INNER JOIN [salephone].[product] p ON od.product_id = p.id
                WHERE od.order_id = @orderId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@orderId", orderId);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var productsInOrder = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var productInOrder = new
                                {
                                    product_name = reader.GetString(reader.GetOrdinal("name")),
                                    quantity = reader.GetInt32(reader.GetOrdinal("quantity")),
                                    total_price = reader.GetDecimal(reader.GetOrdinal("total_price"))
                                };

                                productsInOrder.Add(productInOrder);
                            }

                            return Ok(productsInOrder);
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

        [HttpGet("getAllNumberOfEachProductByMonth/{month}/{year}")]
        public async Task<IActionResult> GetAllNumberOfEachProductByMonth(int month, int year)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT p.name, SUM(od.num) AS total_quantity
                FROM [salephone].[order_detail] od
                INNER JOIN [salephone].[product] p ON od.product_id = p.id
                INNER JOIN [salephone].[order] o ON od.order_id = o.id
                WHERE MONTH(o.created_date) = @month and YEAR(o.created_date) = @year
                GROUP BY p.name;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@month", month);
                        command.Parameters.AddWithValue("@year", year);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var productStatistics = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var productStat = new
                                {
                                    product_name = reader.GetString(reader.GetOrdinal("name")),
                                    total_quantity = reader.GetInt32(reader.GetOrdinal("total_quantity"))
                                };

                                productStatistics.Add(productStat);
                            }

                            return Ok(productStatistics);
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

        [HttpGet("getRevenueByMonth/{month}/{year}")]
        public async Task<IActionResult> GetRevenueByMonth(int month, int year)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT SUM(totalprice) AS total_revenue
                FROM [salephone].[order]
                WHERE MONTH(created_date) = @month and YEAR(created_date) = @year;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@month", month);
                        command.Parameters.AddWithValue("@year", year);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (await reader.ReadAsync())
                            {
                                decimal totalRevenue = reader.GetDecimal(reader.GetOrdinal("total_revenue"));
                                return Ok(new { TotalRevenue = totalRevenue });
                            }
                            else
                            {
                                return Ok(new { TotalRevenue = 0 });
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

    }
}
