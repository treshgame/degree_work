$(document).ready(function() {
    $("#new-cage-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var cageData = {
            cageSize: $("#new-cage-size").val(),
            pricePerDay: $("#new-cage-pricePerDay").val()
        };

        // Validate form data
        if (cageData.pricePerDay <= 0) {
            alert("Цена за день должна быть больше нуля");
            return;
        }

        // Send AJAX POST request
        $.post("http://localhost:8080/owner/add-cage", cageData, function(data) {
            if (data && data.id) {
                // Add new cage to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newSizeInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "cage_size_" + data.id)
                    .attr("id", "cage_size_" + data.id).val(data.cageSize.size)
                    .addClass("form-control").prop("readonly", true)
                );
                let newPriceInput = $("<td>").append(
                    $("<input>").attr("type", "number").attr("name", "cage_pricePerDay_" + data.id)
                    .attr("id", "cage_pricePerDay_" + data.id).val(data.pricePerDay)
                    .addClass("form-control").attr("step", "0.01").attr("min", "0")
                );
                let newStatusInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "cage_status_" + data.id)
                    .attr("id", "cage_status_" + data.id).val(data.cageStatus.status)
                    .addClass("form-control").prop("readonly", true)
                );
                let newLastCleaningTimeInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "cage_lastCleaningTime_" + data.id)
                    .attr("id", "cage_lastCleaningTime_" + data.id).val(data.formattedDateTime)
                    .addClass("form-control").prop("readonly", true)
                );
                let newButtons = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("custom-btn").addClass("update_btn").click(function() {
                        updateCage(data.id);
                    }),
                    $("<button>").attr("type", "button").html("Удалить")
                    .addClass("custom-btn").addClass("delete_btn").click(function() {
                        deleteCage(data.id);
                    })
                );

                newRow.append(newSizeInput).append(newPriceInput).append(newStatusInput)
                      .append(newLastCleaningTimeInput).append(newButtons);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-cage-form")[0].reset();
            } else {
                alert("Ошибка при добавлении клетки");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении клетки");
        });
    });

    function updateCage(cageId) {
        let cageData = {
            id: cageId,
            pricePerDay: $(`#cage_pricePerDay_${cageId}`).val()
        };

        if (cageData.pricePerDay <= 0) {
            alert("Цена за день должна быть больше нуля");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/owner/update-cage",
            type: "PUT",
            data: $.param(cageData),
            success: function(response) {
                console.log("Cage updated successfully:", response);
                alert("Клетка успешно обновлена!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating cage:", xhr.responseText);
                alert("Ошибка при обновлении клетки: " + xhr.responseText);
            }
        });
    }

    function deleteCage(cageId) {
        $.ajax({
            url: "http://localhost:8080/owner/delete-cage",
            type: "DELETE",
            data: { id: cageId },
            success: function(response) {
                console.log("Cage deleted successfully:", response);
                alert("Клетка успешно удалена!");
                $(`#cage_row_${cageId}`).remove();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting cage:", xhr.responseText);
                alert("Ошибка при удалении клетки: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let cageId = $(this).data('id');
        updateCage(cageId);
    });

    $(".delete_btn").click(function(event) {
        event.preventDefault();
        let cageId = $(this).data('id');
        deleteCage(cageId);
    });
});
