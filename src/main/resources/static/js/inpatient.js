$(document).ready(function() {
    // Function to clean a cage
    
    function cleanCage(cageId) {
        $.ajax({
            url: '/inpatient/cageClean',
            type: 'POST',
            data: { cageId: cageId },
            success: function(response) {
                $("#clean_time_" + cageId).text(response)
            },
            error: function(xhr, status, error) {
                alert('Error: ' + xhr.responseText);
            }
        });
    }

    // Example of calling the function with a specific cageId
    $('.clean_btn').click(function() {
        var cageId = $(this).data("id");
        cleanCage(cageId);
    });
});