/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
jUtils = {
    
    executing: function(divId) {
        $('#' + divId).html("<img src=\"./img/loader.gif\" border=\"0\"/>").show();
    },
    
    showing: function(divId, html) {
        $('#' + divId).html(html !== null ? html : '').show();
    },
    
    hiding: function(divId, clean) {
        clean = (clean === false ? false : true);
        $('#' + divId).hide();
        if(clean) {
            $('#' + divId).html('&nbsp;');
        }    
    }
    
};


