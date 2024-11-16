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
    $password = $_POST['password'];

    // Truy vấn để kiểm tra thông tin đăng nhập
    $stmt = $conn->prepare("SELECT password FROM user WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        // Lấy mật khẩu đã lưu từ cơ sở dữ liệu
        $stmt->bind_result($storedPassword);
        $stmt->fetch();

        // Kiểm tra mật khẩu (ở đây không có mã hóa, so sánh trực tiếp)
        if ($password === $storedPassword) {
            echo json_encode(["status" => "success", "message" => "Đăng nhập thành công"]);
        } else {
            echo json_encode(["status" => "error", "message" => "Mật khẩu không đúng"]);
        }
    } else {
        echo json_encode(["status" => "error", "message" => "Email không tồn tại"]);
    }

    // Đóng kết nối
    $stmt->close();
} else {
    echo json_encode(["status" => "error", "message" => "Thiếu thông tin email hoặc mật khẩu"]);
}

$conn->close();
?>
