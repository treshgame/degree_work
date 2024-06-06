$(document).ready(function(){
    $("#new_procedure_btn").click(function(){
        let new_procedure = $("#new_procedure").selected();
        let new_procedure_medication = $("#new_procedure_medication").selected();
        let amount = parseFloat($("#new_medication_amount").val());

        if(amount < 0){
            alert("Количество препарата должно быть больше 0");
            return;
        }
        let procedures_amount = Number($("#procedures_amount").val())
        procedures_amount = $("#procedures_amount").val(procedures_amount + 1);
        let new_row = $("<tr>")
        new_row.attr("data-row", procedures_amount);
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
        let new_procedure_pr = $("<td>").append(
            $("<input>").attr("value", new_procedure.text()).attr("id", "procedure_name_")
            .attr("disabled", true).addClass("form-control")
        );
        
    });
})