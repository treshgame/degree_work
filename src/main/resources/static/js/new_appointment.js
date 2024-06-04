$(document).ready(function(){
    var today = new Date().toISOString().split('T')[0];
    $('#appointment_date').attr('min', today);    
})

function setFreeTime(){
    var selected_date = $("#appointment_date").val();
    var vet_id = $("#vet-select").val();
    console.log("vet id: " + vet_id);
    if(selected_date && vet_id){
        $.get("http://localhost:8080/administrator/free-time", {date : selected_date, vetId : vet_id}, function(response){
            // response.forEach((element) => console.log(element))
            var freeTimeSelect;
            var freeTimeSection = $("#free-time-section")
            if(document.getElementById("appointment_time")){
                freeTimeSelect = $("#appointment_time")
                
            }else{
                $("#free-time-section").addClass("form-section")
                $("#free-time-section").append(
                    $("<label>").html("Свободное время")
                )
                freeTimeSelect = $("<select>")
                    .attr("name", "appointment_time")
                    .attr("id", "appointment_time")
                    .addClass("form-control")
            }
            freeTimeSelect.empty()            
            if(response.length <= 0){
                freeTimeSelect.append($("<option>").val("").html("Нет свободного времени на эту дату"))
                $("#appointment_submit").attr("disabled", true);
            }else{
                response.forEach((element) => {
                    freeTimeSelect.append(
                      $("<option>").val(element).text(element)
                    );
                });
                $("#appointment_submit").attr("disabled", false);
            }
            console.log(freeTimeSelect)
            
            $("#free-time-section").append(freeTimeSelect)
            // console.log(response)
        }).fail(function(xhr, status, error){
            console.error(error);
        })
    }else{
        $("#free-time-section").html("")
        $("#appointment_submit").attr("disabled", true);
    }
}

$("#appointment_date").change(setFreeTime)

$("#vet-select").change(setFreeTime);

