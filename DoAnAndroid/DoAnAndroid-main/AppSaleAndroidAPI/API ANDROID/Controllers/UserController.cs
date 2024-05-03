using API_ANDROID.Model;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace API_ANDROID.Controllers
{
    [Route("api/user")]
    [ApiController]
    public class UserControllernon : ControllerBase
    {
        private readonly IConfiguration _configuration;

        public UserControllernon(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        [HttpGet("getAllUser")]
        public async Task<IActionResult> GetAllUser()
        {
            string connectionString = _configuration.GetConnectionString("SqlServerConnection");
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                       SELECT u.id,u.last_name,u.first_name , u.email, u.username, u.roleid, r.rolename, u.active
                       FROM [salephone].[user] u
                       INNER JOIN [salephone].[role] r ON u.roleid = r.id";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            var users = new List<object>();

                            while (await reader.ReadAsync())
                            {
                                var user = new
                                {
                                    user_id = reader.GetInt32(reader.GetOrdinal("id")),
                                    first_name = reader.GetString(reader.GetOrdinal("first_name")),
                                    last_name = reader.GetString(reader.GetOrdinal("last_name")),
                                    email = reader.GetString(reader.GetOrdinal("email")),
                                    username = reader.GetString(reader.GetOrdinal("username")),
                                    role_name = reader.GetString(reader.GetOrdinal("rolename")),
                                    active = reader.GetBoolean(reader.GetOrdinal("active"))
                                };

                                users.Add(user);
                            }

                            return Ok(users);
                        }
                    }

                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }

        [HttpGet("getUserInfoById/{id}")]
        public async Task<IActionResult> GetUserInfoById(int id)
        {
            string connectionString = _configuration.GetConnectionString("SqlServerConnection");
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT first_name, last_name, email, phone, username, active
                FROM [salephone].[user]
                WHERE id = @id";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", id);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (await reader.ReadAsync())
                            {
                                var userInfo = new
                                {
                                    first_name = reader.GetString(reader.GetOrdinal("first_name")),
                                    last_name = reader.GetString(reader.GetOrdinal("last_name")),
                                    email = reader.GetString(reader.GetOrdinal("email")),
                                    phone = reader.GetString(reader.GetOrdinal("phone")),
                                    username = reader.GetString(reader.GetOrdinal("username")),
                                    active = reader.GetBoolean(reader.GetOrdinal("active"))
                                };

                                return Ok(userInfo);
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

        [HttpPut("updateUserInfoById/{id}")]
        public async Task<IActionResult> UpdateUserInfoById(int id, [FromBody] UpdateUserInfoModel updatedUser)
        {
            if (updatedUser == null)
            {
                return BadRequest("Invalid data.");
            }

            string connectionString = _configuration.GetConnectionString("SqlServerConnection");
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                UPDATE [salephone].[user]
                SET first_name = @first_name,
                    last_name = @last_name,
                    email = @email,
                    phone = @phone,
                    username = @username
                WHERE id = @id";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", id);
                        command.Parameters.AddWithValue("@first_name", updatedUser.first_name);
                        command.Parameters.AddWithValue("@last_name", updatedUser.last_name);
                        command.Parameters.AddWithValue("@email", updatedUser.email);
                        command.Parameters.AddWithValue("@phone", updatedUser.phone);
                        command.Parameters.AddWithValue("@username", updatedUser.username);

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
                SET active = 0
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

        [HttpPut("updateUserPasswordById/{id}")]
        public async Task<IActionResult> UpdateUserPasswordById(int id, [FromBody] ChangePassModel enpassword)
        {
            if (enpassword == null || string.IsNullOrWhiteSpace(enpassword.enpassword))
            {
                return Ok(new { result = "Invalid password." });
            }

            string connectionString = _configuration.GetConnectionString("SqlServerConnection");
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                UPDATE [salephone].[user]
                SET password = @password
                WHERE id = @id";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", id);
                        command.Parameters.AddWithValue("@password", enpassword.enpassword);

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

        private async Task<bool> checkExistUser(string username, string email, SqlConnection connection)
        {
            string query = @"
        SELECT COUNT(*)
        FROM [salephone].[user]
        WHERE username = @Username OR email = @Email
    ";

            using (SqlCommand command = new SqlCommand(query, connection))
            {
                command.Parameters.AddWithValue("@Username", username);
                command.Parameters.AddWithValue("@Email", email);

                int count = (int)await command.ExecuteScalarAsync();
                return count > 0;
            }
        }

        [HttpPut("register")]
        public async Task<IActionResult> Register([FromBody] RegisterModel model)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    if (await checkExistUser(model.user_name, model.email, connection))
                    {
                        return Ok(new { result = "Username or email already exists" });
                    }
                    else
                    {
                        string query = @"
                INSERT INTO [salephone].[user] (first_name, last_name, email, phone, username, [password], created_date, active, roleid)
                VALUES (@FirstName, @LastName, @Email, @Phone, @Username, @Password, @CreatedDate, 1, 2);
            ";

                        using (SqlCommand command = new SqlCommand(query, connection))
                        {
                            command.Parameters.AddWithValue("@FirstName", model.first_name);
                            command.Parameters.AddWithValue("@LastName", model.last_name);
                            command.Parameters.AddWithValue("@Email", model.email);
                            command.Parameters.AddWithValue("@Phone", model.phone);
                            command.Parameters.AddWithValue("@Username", model.user_name);
                            command.Parameters.AddWithValue("@Password", model.password);
                            command.Parameters.AddWithValue("@CreatedDate", DateTime.UtcNow);

                            //int affectedRows = await command.ExecuteNonQueryAsync();

                            if (await command.ExecuteNonQueryAsync() > 0)
                            {
                                return Ok(new { result = "Account created" });
                            }
                            else
                            {
                                return Ok(new { result = "Account create failed" });
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

        [HttpPut("login")]
        public async Task<IActionResult> Login([FromBody] dynamic requestBody)
        {
            try
            {
                string identifier = requestBody.GetProperty("identifier").GetString();
                string password = requestBody.GetProperty("password").GetString();

                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();
                    if (await checkExistUser(identifier, identifier, connection))
                    {
                        string query = @"
                SELECT *
                FROM [salephone].[user]
                WHERE (email = @identifier OR username = @identifier) AND [password] = @password and [active] = 1
            ";

                        using (SqlCommand command = new SqlCommand(query, connection))
                        {
                            command.Parameters.AddWithValue("@identifier", identifier);
                            command.Parameters.AddWithValue("@password", password);

                            using (SqlDataReader reader = await command.ExecuteReaderAsync())
                            {
                                if (await reader.ReadAsync())
                                {
                                    return Ok(new { result = reader.GetInt32(reader.GetOrdinal("id")) });
                                }
                                else
                                {
                                    return Ok(new { result = "Wrong password" });
                                }
                            }
                        }
                    }
                    else
                    {
                        return Ok(new { result = "Account does not exist" });
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
