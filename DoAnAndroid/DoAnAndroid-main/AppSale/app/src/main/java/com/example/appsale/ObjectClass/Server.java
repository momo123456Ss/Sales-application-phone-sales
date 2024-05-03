package com.example.appsale.ObjectClass;

public class Server {
    public static String localhost = "http://emlababia123-001-site1.htempurl.com/api/";
//    public static String localhost = "http://192.168.1.121:7108/api/";
    //admin
    public static String updateUserRoleById = localhost + "admin/updateUserRoleById/";
    public static String getUserRoleByID = localhost + "admin/getUserRoleByID/";
    public static String getNumberUserCreatedOnCurrentMonth = localhost + "admin/getNumberUserCreatedOnCurrentMonth/";
    public static String adminUpdateUserActiveInfoById = localhost + "admin/updateUserActiveInfoById/";

    //Comment
    public static String getAllCommentOfProductByProductId = localhost + "comment/getAllCommentOfProductByProductId/";

    //Manufacturer
    public static String getAllManufacturer = localhost + "Manufacturer/getAllManufacturer/";
    public static String getAllProductOfManufacturerById = localhost + "Manufacturer/getAllProductOfManufacturerById/";

    //Order
    public static String addOrder = localhost + "Order/addOrder/";
    public static String addOrderDetail = localhost + "Order/addOrderDetail/";
    public static String getAllOrderOfUserByUserId = localhost + "Order/getAllOrderOfUserByUserId/";
    public static String getAllProductInOrderById = localhost + "Order/getAllProductInOrderById/";
    public static String getAllNumberOfEachProductByMonth = localhost + "Order/getAllNumberOfEachProductByMonth/";
    public static String getRevenueByMonth = localhost + "Order/getRevenueByMonth/";

    //Product
    public static String getAllProduct = localhost + "Product/getAllProduct/";
    public static String getProductInfoById = localhost + "Product/GetProductInfoById/";
    public static String addProduct = localhost + "Product/addProduct/";
    public static String getNumberProductInInventory = localhost + "Product/getNumberProductInInventory/";
    public static String updateProductNumById = localhost + "Product/updateProductNumById/";
    public static String updateProductPriceById = localhost + "Product/updateProductPriceById/";
    public static String deleteProductById = localhost + "Product/deleteProductById/";

    //User
    public static String getAllUser = localhost + "User/getAllUser/";
    public static String getUserInfoById = localhost + "User/getUserInfoById/";
    public static String updateUserInfoById = localhost + "User/updateUserInfoById/";
    public static String userUpdateUserActiveInfoById = localhost + "User/updateUserActiveInfoById/";
    public static String userUpdateUserPasswordById = localhost + "User/updateUserPasswordById/";
    public static String register = localhost + "User/register/";
    public static String login = localhost + "User/login/";

}
