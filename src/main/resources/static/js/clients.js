$(document).ready(function() {
    $("#new-client-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var clientData = {
            firstName: $("#firstName").val(),
            surname: $("#surname").val(),
            middleName: $("#middleName").val(),
            phoneNumber: $("#phoneNumber").val(),
            email: $("#email").val()
        };

        // Validate form data
        if (clientData.firstName.trim().length < 2 || clientData.surname.trim().length < 2) {
            alert("Имя и фамилия должны быть длиннее");
            return;
        }

        // Send AJAX POST request
        $.post("http://localhost:8080/administrator/add-client", clientData, function(data) {
            if (data && data.id) {
                // Add new client to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newFirstNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "firstName_" + data.id)
                    .attr("id", "firstName_" + data.id).val(data.firstName)
                    .addClass("form-control").prop("required", true)
                );
                let newSurnameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "surname_" + data.id)
                    .attr("id", "surname_" + data.id).val(data.surname)
                    .addClass("form-control").prop("required", true)
                );
                let newMiddleNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "middleName_" + data.id)
                    .attr("id", "middleName_" + data.id).val(data.middleName)
                    .addClass("form-control")
                );
                let newPhoneNumberInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "phoneNumber_" + data.id)
                    .attr("id", "phoneNumber_" + data.id).val(data.phoneNumber)
                    .addClass("form-control").prop("required", true)
                );
                let newEmailInput = $("<td>").append(
                    $("<input>").attr("type", "email").attr("name", "email_" + data.id)
                    .attr("id", "email_" + data.id).val(data.email)
                    .addClass("form-control")
                );
                let newActions = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("btn").addClass("update_btn").click(function() {
                        updateClient(data.id);
                    })
                );

                newRow.append(newFirstNameInput).append(newSurnameInput).append(newMiddleNameInput)
                      .append(newPhoneNumberInput).append(newEmailInput).append(newActions);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-client-form")[0].reset();
            } else {
                alert("Ошибка при добавлении клиента");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении клиента");
        });
    });

    function updateClient(clientId) {
        let clientData = {
            id: clientId,
            firstName: $(`#firstName_${clientId}`).val(),
            surname: $(`#surname_${clientId}`).val(),
            middleName: $(`#middleName_${clientId}`).val(),
            phoneNumber: $(`#phoneNumber_${clientId}`).val(),
            email: $(`#email_${clientId}`).val()
        };

        if (clientData.firstName.trim().length < 2 || clientData.surname.trim().length < 2) {
            alert("Имя и фамилия должны быть длиннее");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/administrator/update-client",
            type: "PUT",
            data: $.param(clientData),
            success: function(response) {
                console.log("Client updated successfully:", response);
                alert("Клиент успешно обновлен!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating client:", xhr.responseText);
                alert("Ошибка при обновлении клиента: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let clientId = $(this).data('id');
        updateClient(clientId);
    });
});
