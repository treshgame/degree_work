function add_btn_click(){
    let amount =  Number($("#rowAmount").val());
    isLastFilled = true;
    if($("#medication_" + amount).find(":selected").val() == 0){
        isLastFilled = false;
    }else if($("#medication_price_" + amount).val() == 0){
        isLastFilled = false;
    }else if($("#medication_amount_" + amount).val() == 0){
        isLastFilled = false;
    }

    if(!isLastFilled){
        return;
    }

    $("#rowAmount").val(amount + 1)
    let newAmount = Number($("#rowAmount").val())
    let newRow = $("<tr>").attr("id", "row_" + newAmount);
    let newMedicationInput = $("<td>").append(
        $("<select>").attr("name", "medication_" + newAmount)
        .attr("id", "medication_" + newAmount).prop("required", true)
        .addClass("form-control")
        .append($("#medication_1 > option").clone())
    );
    let newPriceInput = $("<td>").append(
        $("<input>").attr("type", "number").attr("name", "medication_price_" + newAmount)
        .attr("id", "medication_price_" + newAmount).prop("required", true)
        .attr("min", "0.01").attr("step", "0.01")
        .addClass("form-control")
    );
    let newAmountInput = $("<td>").append(
        $("<input>").attr("type", "number").attr("name", "medication_amount_" + newAmount)
        .attr("id", "medication_amount_" + newAmount).prop("required", true)
        .attr("min", "1").attr("step", "1")
        .addClass("form-control")
    );
    let newAddBtn = $("<td>").append(
        $("<button>").attr("type", "button").html("+")
        .addClass("btn").addClass("add_btn").addClass("table_btn").click(add_btn_click)
    );
    let newRemoveBtn = $("<td>").append(
        $("<button>").attr("type", "button").html("-")
        .attr("data-row", newAmount).attr("id", "remove_btn_" + newAmount)
        .addClass("btn").addClass("remove_btn").addClass("table_btn")
        .click(remove_btn_click)
    );

    newRow.append(newMedicationInput).append(newAmountInput).append(newPriceInput)
    .append(newAddBtn).append(newRemoveBtn);
    $("tbody").append(newRow)
}

function remove_btn_click(){
    let currentButton = $(this);
    let amount = Number($("#rowAmount").val())
    if(amount == 1){
        return
    }
    let rowNumber = Number(currentButton.data("row"))
    $("#rowAmount").val(amount - 1)
    $("#row_" + rowNumber).remove()
    
    // if(rowNumber == amount){
    //     console.log(rowNumber + 1)
    //     return;
    // }

    for(i = Number(currentButton.data("row"))+1; i <= amount; i++){
        console.log(i)
        $("#row_"+i).attr("id", "row_"+(i-1))
        $("#medication_"+i).attr("name", "medication_" + (i-1)).attr("id", "medication_"+(i-1))
        $("#medication_price_" + i).attr("name", "medication_price_" + (i-1)).attr("id", "medication_price_"+(i-1))
        $("#medication_amount_" + i).attr("name", "medication_amount_" + (i-1)).attr("id", "medication_amount_"+(i-1))
        $("#remove_btn_" + i).attr("data-row", i-1).attr("id", "remove_btn_" + (i-1))
    }
}

$(".add_btn").click(add_btn_click)

$(".remove_btn").click(remove_btn_click)
