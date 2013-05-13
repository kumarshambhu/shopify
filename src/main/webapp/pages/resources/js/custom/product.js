$(function() {

    $("#updateProduct,#saveProduct").click(function() {
        if ($("#name").val() == "" || $("#code").val() == "" || $("#price").val() == "") {
            alert("Please fill all mandatory fields!");
            return false;
        }
    });
});

