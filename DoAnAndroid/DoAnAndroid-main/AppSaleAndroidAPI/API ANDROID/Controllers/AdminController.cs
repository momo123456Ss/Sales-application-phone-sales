using API_ANDROID.Model;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace API_ANDROID.Controllers
{
    [Route("api/admin")]
    [ApiController]
    public class AdminController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        public AdminController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        [HttpPut("updateUserRoleById/{id}")]
        public async Task<IActionResult> ChangeRoleUser(int id, [FromBody] ChangeRoleModel model)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                        UPDATE [salephone].[user]
                        SET roleid = @roleID 
                        WHERE id = @UserId;
                    ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@UserId", id);
                        command.Parameters.AddWithValue("@roleID", model.roleid);

                        int affectedRows = await command.ExecuteNonQueryAsync();

                        if (affectedRows > 0)
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

        [HttpGet("getUserRoleByID/{id}")]
        public async Task<IActionResult> GetUserRoleByID(int id)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT roleid
                FROM [salephone].[user]
                WHERE id = @id;
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", id);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (await reader.ReadAsync())
                            {
                                int roleId = reader.GetInt32(reader.GetOrdinal("roleid"));
                                return Ok(new { RoleId = roleId });
                            }
                            else
                            {
                                return Ok(new { result = "User not found" });
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

        [HttpGet("getNumberUserCreatedOnCurrentMonth")]
        public async Task<IActionResult> GetNumberUserCreatedOnCurrentMonth()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT COUNT(*) AS UserCount
                FROM [salephone].[user]
                WHERE YEAR(created_date) = YEAR(GETDATE()) AND MONTH(created_date) = MONTH(GETDATE());
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (await reader.ReadAsync())
                            {
                                int userCount = reader.GetInt32(reader.GetOrdinal("UserCount"));
                                return Ok(new { Tai_khoan_da_tao_trong_thang = userCount });
                            }
                            else
                            {
                                return Ok(new { result = 0 });
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

        [HttpPut("updateUserActiveInfoById/{id}")]
        public async Task<IActionResult> UpdateUserActiveInfoById(int id)
        {
            string connectionString = _configuration.GetConnectionString("SqlServerConnection");
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                UPDATE [salephone].[user]
                SET active = 1
                WHERE id = @id";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", id);

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

    }
}
