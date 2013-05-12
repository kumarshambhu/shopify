$(function() {
    $("#register").click(function() {

        if ($("#email").val() == "" || validateEmail($("#email").val())) {
            alert("Please enter valid Email!");
            return false;
        }

    });
});

function validateEmail(email) {
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    if (!emailReg.test(email)) {
        return true;
    } else {
        return false;
    }
}
