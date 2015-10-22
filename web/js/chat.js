var jChat = {
    newProfile: function(){
        $.ajax({
            url: "index.jsp?action=NewProfile",
            type: "post",
            dataType: "html",            
            data: $.param($("input[type='text']")),
            error: function(hr) {
                alert(hr);
            },
            success: function(html) {
                jUtils.showing("response", html);
            }
        });
    }, 
    
    getProfileList: function(){
        $.ajax({
            url: "index.jsp?action=ProfileList",
            type: "post",
            dataType: "html",
            error: function(hr) {
                alert(hr);
            },
            success: function(html) {
                jUtils.showing("response", html);
            }
        });
    }
};

