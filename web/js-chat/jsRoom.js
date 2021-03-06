var roomVar = {};

$(document).ready(function(){
    roomVar.RELOAD_TIME = 5000;
    roomVar.roomId = $("input[name='roomId']").val();
    roomVar.roomType = $("input[name='roomType']").val();
    roomVar.roomOwner = $("input[name='roomOwner']").val();
    roomVar.privateRoom = roomVar.roomType == "private" ? true : false;
    roomVar.profileType = $("input[name='profileType']").val();
    roomVar.profileId = $("input[name='profileId']").val();
    roomVar.profileLogin = $("input[name='profileLogin']").val();
    roomVar.userAccessId = $("input[name='userAccessId']").val();
    roomVar.availableTags = [];
    
    // null y undefined evaluan en falso en js
    // !! transforma en boolean
    if ( (!! $("input[name='accessDenied']").val()) == false ){
        jsRoom.getMessageList();
        jsRoom.reloadEnableUserAccessPolicy();
        
        if(roomVar.profileType != 'ADMIN' && window.top.jsPresentation.recentlyCreatedTab(roomVar.roomId)){
            jsRoom.sendMessage("<span class='label label-success'>Hi I'm: "+roomVar.profileLogin+"</span>");
        }
    }
    
    if(roomVar.profileType == "ADMIN"){
        jsRoom.getEjectedUserList();
    }
    
    if(roomVar.privateRoom){
        jsRoom.reloadIfExistPrivateRoom();
        
        if(roomVar.roomOwner == roomVar.profileId){
            jsRoom.reloadRejectedInvitations();
        }
    }
    
    //Detect enter-button when the user want send message
    $("#sendMessage").submit(function(event){
       event.preventDefault();
       jsRoom.sendMessage();
    });
    
    //Detect enter-button when the user want invite user to new private room
    $("#createPrivateRoom").submit(function(event){
       event.preventDefault();
       jsRoom.newPrivateRoom();
    });
    
    //Detect enter-button when the user want invite more users to private room
    $("#inviteParticipant").submit(function(event){
       event.preventDefault();
       jsRoom.inviteParticipant();
    });
    
    /*Enable automplete in input text*/
    $("#inviteParticipant #inviteEmail").autocomplete({
        source: roomVar.availableTags
    });
    
    /*******MULTI SELECT*******/
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#inviteParticipant #inviteEmail" )
      // don't navigate away from the field on tab when selecting an item
      .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            roomVar.availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          return false;
        }
      });
      /**************/
});

var jsRoom = {
    
    getMessageList: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetMessageList",
            type: "post",
            dataType: "html",            
            data:  {"roomId":roomVar.roomId, "roomType":roomVar.roomType, "profileId":roomVar.profileId, "profileType":roomVar.profileType},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("messages", html);
                var container = $('#messages div.modal-body'),
                    scrollTo = $('#messages li:last');

                container.scrollTop(
                    scrollTo.offset().top - container.offset().top + container.scrollTop()
                );
            }
        }).success(jsRoom.getParticipantList);
    }, 
    
    getParticipantList: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetParticipantList",
            type: "post",
            dataType: "html",            
            data:  {"roomId":roomVar.roomId, "profileType":roomVar.profileType, "userAccessId":roomVar.userAccessId, "profileId": roomVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("participants", html);
                var cant = $("#participants li[id]").length;

                if(roomVar.privateRoom){
                    if(cant >= 1){
                        $("input[name='message']").removeAttr('disabled');
                        $("input[name='send']").removeAttr('disabled');
                    }else{
                        $("input[name='message']").attr('disabled', 'disabled');
                        $("input[name='send']").attr('disabled', 'disabled');
                    }
                }
            }
        }).success(jsRoom.reloadAccessPolicy);
    },
    
    /**Check if the user has been ejected*/
    reloadAccessPolicy: function(){
        var deferred = $.Deferred();
        $.ajax({
            url: "/chat/index.jsp?action=UpdateCheckAccessPolicy",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId, 'profileId': roomVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html) == "ProfileEjected"){
                    //reject promise for unreload page
                    deferred.reject();
                    $("#myModal .modal-body").text("You have been ejected FROM this room");
                    $("#myModal").modal("show");
                    $("#myModal button").click(function(){
                        jsRoom.sendMessage("<span class='label label-danger'>The user: "+roomVar.profileLogin+" was ejected from this room by ADMIN!</span>")
                              .done(function() {jsRoom.closeRoom(roomVar.roomId);});
                    });
                }else{
                    deferred.resolve();
                }
            }
        });
        deferred.done(jsRoom.getInvitationList);                
    },
    
    getInvitationList: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetInvitationList",
            type: "post",
            dataType: "html",            
            data:  {'profileId':roomVar.profileId, 'callFrom':'room'},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("invitations", html);
            }
        }).success(jsRoom.refreshMessage);
    },
    
    /**Check if the room has benn removed*/
    reloadIfExistPrivateRoom: function(){
        $.ajax({
            url: "/chat/index.jsp?action=UpdateCheckIfExistPrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html) == "not_found"){
                    $("#myModal .modal-body").text("This room has been removed");
                    $("#myModal").modal("show");
                    $("#myModal button").click(function(){
                        window.top.jsPresentation.removeTab(roomVar.roomId);
                    });
                }
            }
        }).success(jsRoom.reloadSearchUserActives);                
    },
    
    reloadSearchUserActives: function(){
        roomVar.availableTags = [];
        $.ajax({
            url: "/chat/index.jsp?action=SearchUsersActives",
            type: "post",
            dataType: "json",            
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {
                if(data != "[]"){
                    $.each(data, function(index, element) {
                        roomVar.availableTags.push(element.login);
                    });
                }
            }
        }).success(jsRoom.refreshIfExistPrivateRoom);                
    },
    
    reloadRejectedInvitations: function(){
        $.ajax({
            url: "/chat/index.jsp?action=UpdateRejectedInvitation",
            type: "post",
            dataType: "json",
            data:  {"roomId" : roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {
                if(data != "[]"){
                    $.each(data, function(index, element) {
                        $("#myModal .modal-body").text("The user: " + element.login + " refuse the invitation to chat.");
                        $("#myModal").modal("show");
                    });
                }
            }
        }).success(jsRoom.refreshRejectedInvitations);  
    },
    
    refreshMessage: function(){
        setTimeout(jsRoom.getMessageList, roomVar.RELOAD_TIME);
    },
    
    refreshIfExistPrivateRoom: function(){
        setTimeout(jsRoom.reloadIfExistPrivateRoom, roomVar.RELOAD_TIME);
    },
    
    refreshRejectedInvitations: function(){
        setTimeout(jsRoom.reloadRejectedInvitations, roomVar.RELOAD_TIME);
    },
    
    newPrivateRoom: function(){
        var title = $("input[name='titleRoom']").val();
        var inviteEmail = $("input[name='inviteEmailRoom']").val();

        $.ajax({
            url: "/chat/index.jsp?action=NewPrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'profileId': roomVar.profileId, 'title': title, 'inviteEmail': inviteEmail},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(roomId) {
                //the call ajax response roomId
                $("input[name='titleRoom']").val("");
                $("input[name='inviteEmailRoom']").val("");
                var roomId = roomId.trim();
                var url = "http://localhost:8080/chat/index.jsp?action=Room&profileId="+roomVar.profileId+"&roomId="+roomId;
                window.top.jsPresentation.setTab(url, "Room: "+title, roomId);
            }
        });
    },
    
    updateStateInvitation: function(room, profile, id, newState, roomName){
        var element = $("#invitations a");
        var room = room;
        var profileId = profile;
        var id = id;
        var newState = newState;

        $.ajax({
            url: "/chat/index.jsp?action=UpdateStateInvitation",
            type: "post",
            dataType: "html",            
            data:  {'id': id, 'newState': newState},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if(newState == "accepted"){
                    element.parents("div#"+id).remove();
                    var url = "http://localhost:8080/chat/index.jsp?action=Room&profileId=" + profileId + "&roomId=" + room;
                    window.top.jsPresentation.setTab(url, "Room: " + roomName, room);
                }else {
                    element.parents("div#"+id).remove();
                }                            
            }
        });
    },
    
    inviteParticipant: function(){
        var participantList = $("input[name='inviteEmailRoom']").val();

        $.ajax({
            url: "/chat/index.jsp?action=InviteParticipant",
            type: "post",
            dataType: "text",            
            data:  {'roomId': roomVar.roomId, 'profileId': roomVar.profileId, 'participantList': participantList},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {
                if(data != "error"){
                    $("input[name='inviteEmailRoom']").val("");
                    $("#myAlert").text("Send invitations success.").addClass("alert-success");
                    $("#myAlert").fadeIn(1000).fadeOut(8000);
                }else{
                    $("input[name='inviteEmailRoom']").val("");
                    $("#myAlert").text("Error, user not found.").addClass("alert-danger");
                    $("#myAlert").fadeIn(1000).fadeOut(8000);
                }
            }
        });
    },
        
    sendMessage: function(msj){
        var message;
        if(msj != null){
            message = msj;
        }else{
            message = $("#sendMessage input[name='message']").val();
        }
        return $.ajax({
            url: "/chat/index.jsp?action=SendMessage",
            type: "post",
            dataType: "html",            
            data: {"roomId":roomVar.roomId, "profileId":roomVar.profileId, "profileLogin":roomVar.profileLogin ,"roomType":roomVar.roomType, "profileType":roomVar.profileType, "message": message},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html)){
                    var message_empty = $("#messages #message_empty").length;
                    if(message_empty == 1){
                        $("#messages ul li:last").remove();
                    }
                    $("#messages ul:last").append(html);
                }
                $("#sendMessage input[name='message']").val("");
            }
        });
    },
 
    deleteMessage: function(messageId){
        var parent = $("#messages li#"+messageId);
        var id = messageId;

        $.ajax({
            url: "/chat/index.jsp?action=DeleteMessage",
            type: "post",
            dataType: "html",            
            data:  {'id': id},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                parent.remove();
            }
        });
    },
    
    deletePrivateRoom: function(){
        $.ajax({
            url: "/chat/index.jsp?action=DeletePrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {                
                window.top.jsPresentation.removeTab(roomVar.roomId);
            }
        });
    },
    
    leavePrivateRoom: function(){
        var deferred = $.Deferred();
        $.ajax({
            url: "/chat/index.jsp?action=LeaveGroup",
            type: "post",
            dataType: "html",            
            data:  {'userAccessId': roomVar.userAccessId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if(roomVar.profileType != 'ADMIN'){
                    jsRoom.sendMessage("<span class='label label-warning'>I'm out! Bye Bye...</span>")
                            .done(function(){ deferred.resolve(); });
                }else{ deferred.resolve(); }
                deferred.done(function(){
                    window.top.jsPresentation.removeTab(roomVar.roomId);
                });
            }
        });
    },
    
    addToInvite: function(email){                    
        $("#createPrivateRoom #inviteEmail").val(email);
    },
    
    addToInviteList: function(email){
        var participantList = $("#inviteParticipant #inviteEmail");

        if(participantList.val() == ""){
            participantList.val(email); 
        }else{
            participantList.val(participantList.val() + ";" + email); 
        }
    }, 
    
    ejectUser: function(roomId, profileId){
        var parent = $("#participants li#"+profileId);
        
        $.ajax({
            url: "/chat/index.jsp?action=EjectUser",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomId, 'profileId': profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                parent.remove();
            }
        });
    },
    
    getEjectedUserList: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetAllEjectedUsers",
            type: "post",
            dataType: "html",            
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("ejectedParticipants", html);
            }
        }).success(jsRoom.refreshEjectedUser);
    },
    
    refreshEjectedUser: function(){
        setTimeout(jsRoom.getEjectedUserList, roomVar.RELOAD_TIME);
    },
    
    enabledUserAgain: function(policyId){
        var parent = $("#ejectedParticipants li#"+policyId);
        
        $.ajax({
            url: "/chat/index.jsp?action=AcceptUserAgain",
            type: "post",
            dataType: "html",            
            data:  {'policyId': policyId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                parent.remove();
            }
        });
    },
    
    /**Check if the enabled user again */
    reloadEnableUserAccessPolicy: function(){
        var deferred = $.Deferred();
        $.ajax({
            url: "/chat/index.jsp?action=UpdateCheckAccessPolicyEnable",
            type: "post",
            dataType: "html",            
            data:  {'profileId': roomVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {   
                if(data != "[]"){
                    $.each(JSON.parse(data), function(index, element) {
                        $("#myModal .modal-body").text("You have been enabled again from room: " + element.roomName);
                        $("#myModal").modal("show");
                        $("#myModal button").click(function(){
                            jsRoom.deleteEnabledUserAgain(element.id);
                        });
                    });
                    deferred.resolve();
                }else{
                    deferred.resolve();
                }
            }
        });
        deferred.done(jsRoom.refreshEnableUserAccessPolicy);                
    },
    
    refreshEnableUserAccessPolicy: function(){
        setTimeout(jsRoom.reloadEnableUserAccessPolicy, roomVar.RELOAD_TIME);
    },
    
    deleteEnabledUserAgain: function(policyId){
        $.ajax({
            url: "/chat/index.jsp?action=DeleteEnableUserPolicy",
            type: "post",
            dataType: "html",            
            data:  {'policyId': policyId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
               console.log("Delete successful....");
            }
        });
    },
    
    closeRoom: function(roomId){
        window.top.jsPresentation.removeTab(roomId);
    }
    
};

        /*
         * Detecta cuando se pulsa una tecla en el input text
         * */
        
//    $("#inviteParticipant #inviteEmail").on('keypress', function(event){
//        var keyPress = String.fromCharCode(event.which); 
//        //var availableTags = [];
//        var input = $.trim($(this).val().split(",").pop());
//        var string_search = input + keyPress;
//        var stringSize = input.length;    
//      
//        if(event.which == 8 || event.which == 32){ //borrado y espacio
//            string_search = input;
//            if(stringSize == 1){
//                string_search = "";
//            }
//        }
//    });