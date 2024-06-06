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
                let newRow = `
                    <tr>
                        <form class="procedure-update-form">
                            <input type="number" value="${data.id}" hidden>
                            <td><input type="text" value="${data.name}" class="form-control" id="procedure_name_${data.id}"></td>
                            <td><input type="number" value="${data.price}" class="form-control" id="procedure_price_${data.id}"></td>
                            <td><input type="submit" class="btn" value="Обновить"></td>
                        </form>
                    </tr>
                `;
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

    $(".procedure-update-form").submit(function(event) {
        event.preventDefault();
        
        // let form = $(this).find;
        let procedureId = $(this).data("id");
        let procedureName = $(`#procedure_name_${procedureId}`).val();
        let procedurePrice = $(`#procedure_price_${procedureId}`).val();
        console.log("form: " + $(this));
        console.log("params: " + $.param(
            {id: procedureId,
                name: procedureName,
                price: procedurePrice
            }
        ))
        $.ajax({
            url: "http://localhost:8080/owner/update-procedure",
            type: "PUT",
            data: $.param(
                {id: procedureId,
                    name: procedureName,
                    price: procedurePrice
                }
            ),
            success: function(response) {
                console.log("Procedure updated successfully:", response);
                alert("Процедура успешно обновлена!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating procedure:", xhr.responseText);
                alert("Ошибка при обновлении процедуры: " + xhr.responseText);
            }
        });
    });
});