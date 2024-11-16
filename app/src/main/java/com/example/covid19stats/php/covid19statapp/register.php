<?php
// Kết nối với MySQL
$conn = new mysqli('127.0.0.1', 'root', '123456', 'covid19statapp');

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối thất bại: " . $conn->connect_error);
}

// Kiểm tra xem các biến POST có tồn tại hay không
if (isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password']; // Không mã hóa mật khẩu

    // Thêm người dùng mới vào cơ sở dữ liệu
    $stmt = $conn->prepare("INSERT INTO user (email, password) VALUES (?, ?)");
    $stmt->bind_param("ss", $email, $password);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Đăng ký thành công"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Đăng ký thất bại"]);
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode(["status" => "error", "message" => "Thiếu thông tin email hoặc mật khẩu"]);
}

$conn->close();
?>
