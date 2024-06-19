$(document).ready(function() {
    $("#new-diagnosis-form").submit(function(event) {
        event.preventDefault();

        // Получаем данные формы
        var diagnosisData = {
            name: $("#new-diagnosis-name").val()
        };

        // Валидация данных формы
        if (diagnosisData.name.trim().length < 2) {
            alert("Название диагноза должно быть длиннее");
            return;
        }

        // Отправка AJAX POST запроса
        $.post("http://localhost:8080/owner/add-diagnosis", diagnosisData, function(data) {
            if (data && data.id) {
                // Добавляем новый диагноз в таблицу
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "diagnosis_name_" + data.id)
                    .attr("id", "diagnosis_name_" + data.id).val(data.name)
                    .addClass("form-control")
                );
                let newUpdateBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("custom-btn update_btn").click(function() {
                        updateDiagnosis(data.id);
                    })
                );
                let newDeleteBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Удалить")
                    .addClass("custom-btn delete_btn").click(function() {
                        deleteDiagnosis(data.id);
                    })
                );

                newRow.append(newNameInput).append(newUpdateBtn).append(newDeleteBtn);
                $(".table tbody").append(newRow);

                // Очищаем форму
                $("#new-diagnosis-form")[0].reset();
            } else {
                alert("Ошибка при добавлении диагноза");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении диагноза");
        });
    });

    function updateDiagnosis(diagnosisId) {
        let diagnosisName = $(`#diagnosis_name_${diagnosisId}`).val();

        $.ajax({
            url: "http://localhost:8080/owner/update-diagnosis",
            type: "PUT",
            data: $.param({
                id: diagnosisId,
                name: diagnosisName
            }),
            success: function(response) {
                console.log("Diagnosis updated successfully:", response);
                alert("Диагноз успешно обновлен!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating diagnosis:", xhr.responseText);
                alert("Ошибка при обновлении диагноза: " + xhr.responseText);
            }
        });
    }

    function deleteDiagnosis(diagnosisId) {
        $.ajax({
            url: "http://localhost:8080/owner/delete-diagnosis",
            type: "DELETE",
            data: { id: diagnosisId },
            success: function(response) {
                console.log("Diagnosis deleted successfully:", response);
                alert("Диагноз успешно удален!");
                $(`#row_${diagnosisId}`).remove();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting diagnosis:", xhr.responseText);
                alert("Ошибка при удалении диагноза: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let diagnosisId = $(this).data('id');
        updateDiagnosis(diagnosisId);
    });

    $(".delete_btn").click(function(event) {
        event.preventDefault();
        let diagnosisId = $(this).data('id');
        deleteDiagnosis(diagnosisId);
    });
});
