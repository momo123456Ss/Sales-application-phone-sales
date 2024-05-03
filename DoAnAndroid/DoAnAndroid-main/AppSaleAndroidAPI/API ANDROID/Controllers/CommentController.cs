using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace API_ANDROID.Controllers
{
    [Route("api/comment")]
    [ApiController]
    public class CommentController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        public CommentController(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        [HttpGet("getAllCommentOfProductByProductId/{productId}")]
        public async Task<IActionResult> GetAllCommentOfProductByProductId(int productId)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT c.[content] AS content, u.username AS user_name, c.star AS star
                FROM [salephone].[comment] c
                INNER JOIN [salephone].[user] u ON c.user_id = u.id
                WHERE c.product_id = @productId;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@productId", productId);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var comments = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var comment = new
                                {
                                    content = reader.GetString(reader.GetOrdinal("content")),
                                    user_name = reader.GetString(reader.GetOrdinal("user_name")),
                                    star = reader.GetInt32(reader.GetOrdinal("star"))
                                };

                                comments.Add(comment);
                            }

                            return Ok(comments);
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
