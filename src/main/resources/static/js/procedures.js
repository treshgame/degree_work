$(document).ready(function() {
    $("#new-procedure-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var procedureName = $("#new-procedure-name").val();
        var procedurePrice = parseFloat($("#new-procedure-price").val());

        // Validate form data
        if (procedureName.trim().length < 2) {
            alert("Слишком короткое название процедуры");
            return;
        }

        if (procedurePrice < 0.01) {
            alert("Цена должны быть больше 0");
            return;
        }

        // Create JSON object
        let procedureData = {
            name: procedureName,
            price: procedurePrice
        };

        // Send AJAX POST request
        $.post("http://localhost:8080/owner/add-procedure", procedureData, function(data) {
            if (data && data.id) {
                // Add new procedure to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "procedure_name_" + data.id)
                    .attr("id", "procedure_name_" + data.id).val(data.name)
                    .addClass("form-control")
                );
                let newPriceInput = $("<td>").append(
                    $("<input>").attr("type", "number").attr("name", "procedure_price_" + data.id)
                    .attr("id", "procedure_price_" + data.id).val(data.price)
                    .attr("min", "0.01").attr("step", "0.01")
                    .addClass("form-control")
                );
                let newUpdateBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("btn").addClass("update_btn").click(function() {
                        updateProcedure(data.id);
                    })
                );

                newRow.append(newNameInput).append(newPriceInput).append(newUpdateBtn);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-procedure-form")[0].reset();
            } else {
                alert("Ошибка при добавлении процедуры");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении процедуры");
        });
    });

    function updateProcedure(procedureId) {
        let procedureName = $(`#procedure_name_${procedureId}`).val();
        let procedurePrice = $(`#procedure_price_${procedureId}`).val();

        $.ajax({
            url: "http://localhost:8080/owner/update-procedure",
            type: "PUT",
            data: $.param({
                id: procedureId,
                name: procedureName,
                price: procedurePrice
            }),
            success: function(response) {
                console.log("Procedure updated successfully:", response);
                alert("Процедура успешно обновлена!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating procedure:", xhr.responseText);
                alert("Ошибка при обновлении процедуры: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let procedureId = $(this).data('id');
        updateProcedure(procedureId);
    });
});
