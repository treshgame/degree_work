$(document).ready(function() {
    $("#new-supplier-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var supplierName = $("#new-supplier-name").val();
        var supplierPhoneNumber = $("#new-supplier-phoneNumber").val();
        var supplierEmail = $("#new-supplier-email").val();

        // Validate form data
        if (supplierName.trim().length < 2) {
            alert("Слишком короткое название поставщика");
            return;
        }

        // Create JSON object
        let supplierData = {
            name: supplierName,
            phoneNumber: supplierPhoneNumber,
            email: supplierEmail
        };

        // Send AJAX POST request
        $.post("http://localhost:8080/owner/add-supplier", supplierData, function(data) {
            if (data && data.id) {
                // Add new supplier to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "supplier_name_" + data.id)
                    .attr("id", "supplier_name_" + data.id).val(data.name)
                    .addClass("form-control")
                );
                let newPhoneNumberInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "supplier_phoneNumber_" + data.id)
                    .attr("id", "supplier_phoneNumber_" + data.id).val(data.phoneNumber)
                    .addClass("form-control")
                );
                let newEmailInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "supplier_email_" + data.id)
                    .attr("id", "supplier_email_" + data.id).val(data.email)
                    .addClass("form-control")
                );
                let newUpdateBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("btn").addClass("update_btn").click(function() {
                        updateSupplier(data.id);
                    })
                );

                newRow.append(newNameInput).append(newPhoneNumberInput).append(newEmailInput).append(newUpdateBtn);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-supplier-form")[0].reset();
            } else {
                alert("Ошибка при добавлении поставщика");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении поставщика");
        });
    });

    function updateSupplier(supplierId) {
        let supplierName = $(`#supplier_name_${supplierId}`).val();
        let supplierPhoneNumber = $(`#supplier_phoneNumber_${supplierId}`).val();
        let supplierEmail = $(`#supplier_email_${supplierId}`).val();
        
        $.ajax({
            url: "http://localhost:8080/owner/update-supplier",
            type: "PUT",
            data: $.param({
                id: supplierId,
                name: supplierName,
                phoneNumber: supplierPhoneNumber,
                email: supplierEmail
            }),
            success: function(response) {
                console.log("Supplier updated successfully:", response);
                alert("Поставщик успешно обновлен!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating supplier:", xhr.responseText);
                alert("Ошибка при обновлении поставщика: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let supplierId = $(this).data('id');
        updateSupplier(supplierId);
    });
});
