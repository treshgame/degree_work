$(document).ready(function() {
    $("#new-medication-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var medicationName = $("#new-medication-name").val();
        var medicationUnit = $("#new-medication-unit").val();

        // Validate form data
        if (medicationName.trim().length < 2) {
            alert("Слишком короткое название медикамента");
            return;
        }

        // Create JSON object
        let medicationData = {
            name: medicationName,
            unit: medicationUnit
        };

        // Send AJAX POST request
        $.post("http://localhost:8080/owner/add-medication", medicationData, function(data) {
            if (data && data.id) {
                // Add new medication to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "medication_name_" + data.id)
                    .attr("id", "medication_name_" + data.id).val(data.name)
                    .addClass("form-control")
                );
                let newUnitSelect = $("<td>").append(
                    $("<select>").attr("name", "medication_unit_" + data.id)
                    .attr("id", "medication_unit_" + data.id)
                    .addClass("form-control")
                    .append($("#new-medication-unit > option").clone().prop('selected', function() {
                        return this.value == data.unit;
                    }))
                );
                let newUpdateBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("btn").addClass("update_btn").click(function() {
                        updateMedication(data.id);
                    })
                );

                newRow.append(newNameInput).append(newUnitSelect).append(newUpdateBtn);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-medication-form")[0].reset();
            } else {
                alert("Ошибка при добавлении медикамента");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении медикамента");
        });
    });

    function updateMedication(medicationId) {
        let medicationName = $(`#medication_name_${medicationId}`).val();
        let medicationUnit = $(`#medication_unit_${medicationId}`).val();

        $.ajax({
            url: "http://localhost:8080/owner/update-medication",
            type: "PUT",
            data: $.param({
                id: medicationId,
                name: medicationName,
                unit: medicationUnit
            }),
            success: function(response) {
                console.log("Medication updated successfully:", response);
                alert("Медикамент успешно обновлен!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating medication:", xhr.responseText);
                alert("Ошибка при обновлении медикамента: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let medicationId = $(this).data('id');
        updateMedication(medicationId);
    });
});
