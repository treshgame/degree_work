$(document).ready(function() {
    $("#new-animal-kind-form").submit(function(event) {
        event.preventDefault();

        // Получаем данные формы
        var animalKindData = {
            name: $("#new-animal-kind-name").val()
        };

        // Валидация данных формы
        if (animalKindData.name.trim().length < 2) {
            alert("Название вида должно быть длиннее");
            return;
        }

        // Отправка AJAX POST запроса
        $.post("http://localhost:8080/owner/add-animal-kind", animalKindData, function(data) {
            if (data && data.id) {
                // Добавляем новый вид животного в таблицу
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "animal_kind_name_" + data.id)
                    .attr("id", "animal_kind_name_" + data.id).val(data.name)
                    .addClass("form-control")
                );
                let newUpdateBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("custom-btn update_btn").click(function() {
                        updateAnimalKind(data.id);
                    })
                );
                let newDeleteBtn = $("<td>").append(
                    $("<button>").attr("type", "button").html("Удалить")
                    .addClass("custom-btn delete_btn").click(function() {
                        deleteAnimalKind(data.id);
                    })
                );

                newRow.append(newNameInput).append(newUpdateBtn).append(newDeleteBtn);
                $(".table tbody").append(newRow);

                // Очищаем форму
                $("#new-animal-kind-form")[0].reset();
            } else {
                alert("Ошибка при добавлении вида животного");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении вида животного");
        });
    });

    function updateAnimalKind(animalKindId) {
        let animalKindName = $(`#animal_kind_name_${animalKindId}`).val();

        $.ajax({
            url: "http://localhost:8080/owner/update-animal-kind",
            type: "PUT",
            data: $.param({
                id: animalKindId,
                name: animalKindName
            }),
            success: function(response) {
                console.log("Animal kind updated successfully:", response);
                alert("Вид животного успешно обновлен!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating animal kind:", xhr.responseText);
                alert("Ошибка при обновлении вида животного: " + xhr.responseText);
            }
        });
    }

    function deleteAnimalKind(animalKindId) {
        $.ajax({
            url: "http://localhost:8080/owner/delete-animal-kind",
            type: "DELETE",
            data: { id: animalKindId },
            success: function(response) {
                console.log("Animal kind deleted successfully:", response);
                alert("Вид животного успешно удален!");
                $(`#row_${animalKindId}`).remove();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting animal kind:", xhr.responseText);
                alert("Ошибка при удалении вида животного: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let animalKindId = $(this).data('id');
        updateAnimalKind(animalKindId);
    });

    $(".delete_btn").click(function(event) {
        event.preventDefault();
        let animalKindId = $(this).data('id');
        deleteAnimalKind(animalKindId);
    });
});
