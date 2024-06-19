$(document).ready(function() {
    $("#new-animal-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var animalData = {
            name: $("#name").val(),
            kind: $("#kind").val(),
            breed: $("#breed").val(),
            birthday: $("#birthday").val(),
            clientId: $("#client").val()
        };

        // Validate form data
        if (animalData.name.trim().length < 2) {
            alert("Кличка должна быть длиннее");
            return;
        }

        if (animalData.kind.trim().length < 2) {
            alert("Название вида должно быть длиннее");
            return;
        }

        if(animalData.breed.trim().length < 2){
            alert("Название породы должно быть длиннее")
            return;
        }

        if($("#birthday").val().length == 0){
            alert("Не указана дата");
            return;
        }

        // Send AJAX POST request
        $.post("http://localhost:8080/administrator/add-animal", animalData, function(data) {
            if (data && data.id) {
                // Add new animal to the table
                let newRow = $("<tr>").attr("id", "row_" + data.id);
                let newNameInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "name_" + data.id)
                    .attr("id", "name_" + data.id).val(data.name)
                    .addClass("form-control").prop("required", true)
                );
                let newKindInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "kind_" + data.id)
                    .attr("id", "kind_" + data.id).val(data.kind)
                    .addClass("form-control").prop("required", true)
                );
                let newBreedInput = $("<td>").append(
                    $("<input>").attr("type", "text").attr("name", "breed_" + data.id)
                    .attr("id", "breed_" + data.id).val(data.breed)
                    .addClass("form-control").prop("required", true)
                );
                let newBirthdayInput = $("<td>").append(
                    $("<input>").attr("type", "date").attr("name", "birthday_" + data.id)
                    .attr("id", "birthday_" + data.id).val(data.birthday)
                    .addClass("form-control").prop("required", true)
                );
                let newOwnerSelect = $('select').append($("#client > option").clone()).attr("name", "client_" + data.id)
                    .attr("id", "client_" + data.id)
                    .addClass("form-control").prop('selected', function() {
                        return this.value == data.client.id;
                    });

                let newClientSelect = $("<td>").append(newOwnerSelect);
                let newActions = $("<td>").append(
                    $("<button>").attr("type", "button").html("Обновить")
                    .addClass("custom-btn").addClass("update_btn").click(function() {
                        updateAnimal(data.id);
                    }),
                );

                newRow.append(newNameInput).append(newKindInput).append(newBreedInput)
                      .append(newBirthdayInput).append(newClientSelect).append(newActions);
                $(".table tbody").append(newRow);

                // Clear the form
                $("#new-animal-form")[0].reset();

                alert("Животное успешно добавлена")
            } else {
                alert("Ошибка при добавлении животного");
            }
        }).fail(function(error) {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении животного");
        });
});

    function updateAnimal(animalId) {
        let animalData = {
            id: animalId,
            name: $(`#name_${animalId}`).val(),
            kind: $(`#kind_${animalId}`).val(),
            breed: $(`#breed_${animalId}`).val(),
            birthday: $(`#birthday_${animalId}`).val(),
            clientId: $(`#client_${animalId}`).val()
        };

        if (animalData.name.trim().length < 2) {
            alert("Кличка должна быть длиннее");
            return;
        }

        if (animalData.kind.trim().length < 2) {
            alert("Название вида должно быть длиннее");
            return;
        }

        if(animalData.breed.trim().length < 2){
            alert("Название породы должно быть длиннее")
            return;
        }

        $.ajax({
            url: "http://localhost:8080/administrator/update-animal",
            type: "PUT",
            data: $.param(animalData),
            success: function(response) {
                console.log("Animal updated successfully:", response);
                alert("Животное успешно обновлено!");
            },
            error: function(xhr, status, error) {
                console.error("Error updating animal:", xhr.responseText);
                alert("Ошибка при обновлении животного: " + xhr.responseText);
            }
        });
    
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let animalId = $(this).data('id');
        updateAnimal(animalId);
    });
});

