$(function() {

    $("form input").keypress(function(e) {
        if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
            $("#login").click();
            return false;
        } else {
            return true;
        }
    });

    $("#login").click(function() {

        if ($("#captchaStatus").val() === "false") {
            if ($("#loginAttempt").val() >= CAPTACH_ATTEMPT_INTERVAL) {
                //READ:Validate captcha if attempt is greater than 3.
                event.preventDefault();
                validateCaptcha();
                return false;
            }
        }
        return true;

    });


});

var RecaptchaOptions = {
    theme: 'white'
};


function validateCaptcha() {
    var challenge = Recaptcha.get_challenge();
    var response = Recaptcha.get_response();

    $.ajax({
        type: "POST",
        url: BASE_URL + "/login",
        cache: false,
        dataType: "html",
        async: false,
        data: ({
            'action': 'Captcha',
            recaptcha_challenge_field: challenge,
            recaptcha_response_field: response
        }),
        error: function(msg, error) {
            alert(error);
        },
        success: function(html) {
            //alert(html);
            if (html === "true") {
                $("#captchaStatus").val("true");
                $("#login").click();
            } else {
                Recaptcha.reload();
            }
        }
    });

}