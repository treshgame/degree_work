$(document).ready(function() {
    $("#new-user-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var userData = {
            username: $("#username").val(),
            password: $("#password").val(),
            employeeId: $("#employee").val()
        };

        // Validate form data
        if (userData.username.trim().length < 2) {
            alert("Username must be at least 2 characters long");
            return;
        }

        // Check if the username is unique
        $.get("http://localhost:8080/owner/check-username", {username: userData.username}, function(response) {
            if (response.exists) {
                alert("A user with this username already exists");
            } else {
                // Send AJAX POST request
                $.post("http://localhost:8080/owner/add-user", userData, function(data) {
                    if (data && data.id) {
                        // Add new user to the table
                        let newRow = $("<tr>").attr("id", "row_" + data.id);
                        let newUsernameInput = $("<td>").append(
                            $("<input>").attr("type", "text").attr("name", "username_" + data.id)
                            .attr("id", "username_" + data.id).val(data.username)
                            .addClass("form-control").prop("required", true)
                        );
                        let newEmployeeCell = $("<td>").text(data.employee.firstName + ' ' + data.employee.surname);
                        let newActions = $("<td>").append(
                            $("<button>").attr("type", "button").html("Обновить")
                            .addClass("custom-btn").addClass("update_btn").click(function() {
                                updateUser(data.id);
                            }),
                            $("<button>").attr("type", "button").html("Поменять пароль")
                            .addClass("custom-btn").addClass("change_password_btn").click(function() {
                                changePassword(data.id);
                            }),
                            $("<button>").attr("type", "button").html("Удалить")
                            .addClass("custom-btn").addClass("delete_btn").click(function() {
                                deleteUser(data.id);
                            })
                        );

                        newRow.append(newUsernameInput).append(newEmployeeCell).append(newActions);
                        $(".table tbody").append(newRow);

                        // Clear the form
                        $("#new-user-form")[0].reset();
                    } else {
                        alert("Error adding user");
                    }
                }).fail(function(error) {
                    console.error("Error:", error);
                    alert("Error adding user");
                });
            }
        });
    });

    function updateUser(userId) {
        let username = $(`#username_${userId}`).val();

        if (username.trim().length < 2) {
            alert("Username must be at least 2 characters long");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/owner/update-user",
            type: "PUT",
            data: { id: userId, username: username },
            success: function(response) {
                console.log("User updated successfully:", response);
                alert("User updated successfully!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating user:", xhr.responseText);
                alert("Error updating user: " + xhr.responseText);
            }
        });
    }

    function changePassword(userId) {
        let newPassword = prompt("Введите новый пароль:");

        if (newPassword.trim().length < 4) {
            alert("Пароль должен быть не короче 4 символов");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/owner/change-password",
            type: "PUT",
            data: { id: userId, password: newPassword },
            success: function(response) {
                console.log("Password changed successfully:", response);
                alert("Пароль успешно сменен!");
            },
            error: function(xhr, status, error) {
                console.error("Error changing password:", xhr.responseText);
                alert("Ошибка во время смены пароля: " + xhr.responseText);
            }
        });
    }

    function deleteUser(userId) {
        $.ajax({
            url: "http://localhost:8080/owner/delete-user",
            type: "DELETE",
            data: { id: userId },
            success: function(response) {
                console.log("User deleted successfully:", response);
                alert("Пользователь успешно удален!");
                $(`#row_${userId}`).remove();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting user:", xhr.responseText);
                alert("Пользователь не удален: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let userId = $(this).data('id');
        updateUser(userId);
    });

    $(".change_password_btn").click(function(event) {
        event.preventDefault();
        let userId = $(this).data('id');
        changePassword(userId);
    });

    $(".delete_btn").click(function(event) {
        event.preventDefault();
        let userId = $(this).data('id');
        deleteUser(userId);
    });
});
