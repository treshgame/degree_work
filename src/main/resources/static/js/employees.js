$(document).ready(function() {
    $("#new-employee-form").submit(function(event) {
        event.preventDefault();

        // Get form data
        var employeeData = {
            firstName: $("#new-employee-firstName").val(),
            surname: $("#new-employee-surname").val(),
            middleName: $("#new-employee-middleName").val(),
            job: $("#new-employee-job").val(),
            login: $("#new-employee-login").val(),
            password: $("#new-employee-password").val()
        };

        // Validate form data
        if (employeeData.firstName.trim().length < 2 || employeeData.surname.trim().length < 2) {
            alert("Имя и фамилия должны быть длиннее");
            return;
        }

        // Check if the username is unique
        $.get("http://localhost:8080/owner/check-username", {username: employeeData.login}, function(response) {
            if (response.exists) {
                alert("Пользователь с таким логином уже существует");
            } else {
                // Send AJAX POST request
                $.post("http://localhost:8080/owner/add-employee", employeeData, function(data) {
                    if (data && data.id) {
                        // Add new employee to the table
                        let newRow = $("<tr>").attr("id", "row_" + data.id);
                        let newFirstNameInput = $("<td>").append(
                            $("<input>").attr("type", "text").attr("name", "employee_firstName_" + data.id)
                            .attr("id", "employee_firstName_" + data.id).val(data.firstName)
                            .addClass("form-control")
                        );
                        let newSurnameInput = $("<td>").append(
                            $("<input>").attr("type", "text").attr("name", "employee_surname_" + data.id)
                            .attr("id", "employee_surname_" + data.id).val(data.surname)
                            .addClass("form-control")
                        );
                        let newMiddleNameInput = $("<td>").append(
                            $("<input>").attr("type", "text").attr("name", "employee_middleName_" + data.id)
                            .attr("id", "employee_middleName_" + data.id).val(data.middleName)
                            .addClass("form-control")
                        );
                        let newJobSelect = $("<td>").append(
                            $("<select>").attr("name", "employee_job_" + data.id)
                            .attr("id", "employee_job_" + data.id)
                            .addClass("form-control")
                            .append($("#new-employee-job > option").clone().prop('selected', function() {
                                return this.value == data.job;
                            }))
                        );
                        let newJobStart = $("<td>").text(data.jobStart);
                        let newTimeQuited = $("<td>").attr("id", "employee_timeQuited_td_" + data.id).text('Работает');
                        let newStatusSelect = $("<td>").append(
                            $("<select>").attr("name", "employee_status_" + data.id)
                            .attr("id", "employee_status_" + data.id)
                            .addClass("form-control employee-status")
                            .attr("data-id", data.id)
                            .append($("#new-employee-status > option").clone().prop('selected', function() {
                                return this.value == data.status;
                            }))
                        );
                        let newUpdateBtn = $("<td>").append(
                            $("<button>").attr("type", "button").html("Обновить")
                            .addClass("btn").addClass("update_btn").click(function() {
                                updateEmployee(data.id);
                            })
                        );

                        newRow.append(newFirstNameInput).append(newSurnameInput).append(newMiddleNameInput)
                              .append(newJobSelect).append(newJobStart).append(newTimeQuited)
                              .append(newStatusSelect).append(newUpdateBtn);
                        $(".table tbody").append(newRow);

                        // Clear the form
                        $("#new-employee-form")[0].reset();
                    } else {
                        alert("Ошибка при добавлении сотрудника");
                    }
                }).fail(function(error) {
                    console.error("Ошибка:", error);
                    alert("Ошибка при добавлении сотрудника");
                });
            }
        });
    });

    function updateEmployee(employeeId) {
        let employeeData = {
            id: employeeId,
            firstName: $(`#employee_firstName_${employeeId}`).val(),
            surname: $(`#employee_surname_${employeeId}`).val(),
            middleName: $(`#employee_middleName_${employeeId}`).val(),
            job: $(`#employee_job_${employeeId}`).val(),
            status: $(`#employee_status_${employeeId}`).val(),
            timeQuited: $(`#employee_timeQuited_${employeeId}`).val()
        };

        if (employeeData.status === 'FIRED' && !employeeData.timeQuited) {
            employeeData.timeQuited = new Date().toISOString().split('T')[0]; // Current date
        } else if (employeeData.status !== 'FIRED') {
            employeeData.timeQuited = null;
        }

        $.ajax({
            url: "http://localhost:8080/owner/update-employee",
            type: "PUT",
            data: $.param(employeeData),
            success: function(response) {
                console.log("Employee updated successfully:", response);
                alert("Сотрудник успешно обновлен!");
                if (employeeData.status === 'FIRED') {
                    $(`#employee_timeQuited_td_${employeeId}`).html(
                        `<input type="date" class="form-control" id="employee_timeQuited_${employeeId}" value="${employeeData.timeQuited}">`
                    );
                } else {
                    $(`#employee_timeQuited_td_${employeeId}`).text('Работает');
                }
            },
            error: function(xhr, status, error) {
                console.error("Error updating employee:", xhr.responseText);
                alert("Ошибка при обновлении сотрудника: " + xhr.responseText);
            }
        });
    }

    $(".update_btn").click(function(event) {
        event.preventDefault();
        let employeeId = $(this).data('id');
        updateEmployee(employeeId);
    });

    $(document).on('change', '.employee-status', function() {
        let employeeId = $(this).data('id');
        let status = $(this).val();
        let timeQuitedTd = $(`#employee_timeQuited_td_${employeeId}`);

        if (status === 'FIRED') {
            let timeQuitedInput = $("<input>").attr("type", "date").attr("name", "employee_timeQuited_" + employeeId)
                                               .attr("id", "employee_timeQuited_" + employeeId)
                                               .addClass("form-control")
                                               .val(new Date().toISOString().split('T')[0]); // Current date
            timeQuitedTd.html(timeQuitedInput);
        } else {
            timeQuitedTd.html('<span>Работает</span>');
        }
    });
});
