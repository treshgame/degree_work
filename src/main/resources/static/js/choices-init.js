$(document).ready(function(){
    $('select').each(function(){
        new Choices($(this)[0],{
            placeholder: false,
            noResultsText: "Результатов не найдено",
            noChoicesText: "Ничего не выбрано",
            itemSelectText: ""
        });
    })
});