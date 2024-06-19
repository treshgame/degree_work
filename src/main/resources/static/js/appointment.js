$(document).ready(function(){
    $("#new_procedure_btn").click(function(event){
        event.preventDefault();
        let new_procedure = $("#new_procedure").find(":selected");
        let new_procedure_medication = $("#new_procedure_medication").find(":selected");
        if($("#new_medication_amount").val().trim() == ""){
            alert("Не указано количество")
            return;
        }
        let amount = parseFloat($("#new_medication_amount").val());
        let max_medication = Number($("#medication_option_" + new_procedure_medication.val()).data("amount"));
        
        if(amount == NaN){
            alert("Количество должно быть числом")
            return;
        }

        if(amount <= 0){
            alert("Количество препарата должно быть больше 0");
            return;
        }

        if(amount > max_medication){
            alert("Доступно только " + max_medication + " единиц препарата");
            return;
        }

        $("#medication_option_" + new_procedure_medication.val()).data("amount", max_medication - amount);
        let procedures_amount = Number($("#procedures_amount").val())
        $("#procedures_amount").val(procedures_amount + 1);
        procedures_amount = Number($("#procedures_amount").val());
        let new_row = $("<tr>")
        new_row.attr("id", "row_" + procedures_amount).attr("data-row", procedures_amount);

        new_procedure.append(
            $("<input>").attr("id", "procedure_id_" + procedures_amount)
                .attr("name", "procedure_id_" + procedures_amount)
                .attr("value", new_procedure.val()).attr("hidden", true)
        );

        new_procedure.append(
            $("<input>").attr("id", "procedure_medication_id_" + procedures_amount)
                .attr("name", "procedure_medication_id_" + procedures_amount)
                .attr("value", new_procedure_medication.val()).attr("hidden", true)
        );

        let new_procedure_td = $("<td>").append(
            
            $("<input>").attr("value", new_procedure.text())
                .attr("readonly", true).addClass("form-control")
        );

        let new_procedure_medication_td = $("<td>").append(
            $("<input>").attr("value", new_procedure_medication.text())
                .attr("readonly", true).addClass("form-control")
        );

        let new_procedure_medication_amount_td = $("<td>").append(
            $("<input>").attr("value", amount)
                .attr("id", "procedure_medication_amount_" + procedures_amount)
                .attr("name", "procedure_medication_amount_" + procedures_amount)
                .attr("readonly", true).addClass("form-control")
        );
        
        let new_row_btn = $("<td>").append(
            $("<button>").attr("data-row", procedures_amount).attr("data-medication", new_procedure.val())
                .html("-").addClass("custom-btn").addClass("table-btn").addClass("delete-procedure")
                .click(function(event) {
                    event.preventDefault()
                    let medication_id = $(new_procedure_medication).val();
                    let old_max_amount = Number($("#medication_option_" + medication_id).data("amount"));
                    console.log("old max amount: " + old_max_amount)
                    let row_number = Number($(this).data("row"));
                    
                    let old_amount = Number($("#procedure_medication_amount_" + row_number).val());
                    $("#medication_option_" + medication_id).data("amount", old_max_amount + old_amount)
            
                    $("#row_" + row_number).remove();
                })
        )

        new_row.append(new_procedure_td).append(new_procedure_medication_td)
            .append(new_procedure_medication_amount_td).append(new_row_btn);
        $("#procedures_tbody").append(new_row)
    });

    $('#sendToInpatient').change(function() {
        if ($(this).is(':checked')) {
            $('#cage_selection').show();
        } else {
            $('#cage_selection').hide();
        }
    });
})