var roomVar = {};

$(document).ready(function(){
    roomVar.RELOAD_TIME = 15000;
    roomVar.roomId = $("input[name='roomId']").val();
    roomVar.roomType = $("input[name='roomType']").val();
    roomVar.privateRoom = roomVar.roomType == "private" ? true : false;
    roomVar.profileType = $("input[name='profileType']").val();
    roomVar.profileId = $("input[name='profileId']").val();
    roomVar.userAccessId = $("input[name='userAccessId']").val();
    
    // null y undefined evaluan en falso en js
    // !! transforma en boolean
    if ( (!! $("input[name='accessDenied']").val()) == false ){
        jsRoom.getMessageList();
        jsRoom.getParticipantList();
        jsRoom.getPublicRoomByProfile();
        jsRoom.reloadAccessPolicy();

    }
    
    if(roomVar.privateRoom){
        jsRoom.getAllLoguedParticipantList();
        jsRoom.reloadIfExistPrivateRoom();
        jsRoom.reloadRejectedInvitations();
    }
});

var jsRoom = {
    
    getMessageList: function(){
        $.ajax({
            url: "index.jsp?action=GetMessageList",
            type: "post",
            dataType: "html",            
            data:  {"roomId":roomVar.roomId, "roomType":roomVar.roomType, "profileId":roomVar.profileId, "profileType":roomVar.profileType},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("messages", html);
            }
        }).success(jsRoom.refreshMessage);

    }, 
    
    getPublicRoomByProfile: function(){
        $.ajax({
            url: "index.jsp?action=GetPublicRoomByProfile",
            type: "post",
            dataType: "html",            
            data:  {'profileId':roomVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("participateRoom", html);
            }
        });
    },
    
    getParticipantList: function(){
        $.ajax({
            url: "index.jsp?action=GetParticipantList",
            type: "post",
            dataType: "html",            
            data:  {"roomId":roomVar.roomId, "profileType":roomVar.profileType, "userAccessId":roomVar.userAccessId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("participants", html);
                var cant = $("#participants tr[id]").length;

                if(roomVar.privateRoom){
                    if(cant > 1){
                        $("input[name='message']").removeAttr('disabled');
                        $("input[name='send']").removeAttr('disabled');
                    }else{
                        $("input[name='message']").attr('disabled', 'disabled');
                        $("input[name='send']").attr('disabled', 'disabled');
                    }
                }
            }
        }).success(jsRoom.refreshParticipants);
    },
    
    getAllLoguedParticipantList: function(){
        $.ajax({
            url: "index.jsp?action=GetAllLoguedParticipantList",
            type: "post",
            dataType: "html",            
            data:  {"roomId":roomVar.roomId, "profileType":roomVar.profileType, "userAccessId":roomVar.userAccessId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("allLoguedParticipants", html);
            }
        }).success(jsRoom.refreshAllLoguedParticipants);
    },
    
    /**Check if the user has been ejected*/
    reloadAccessPolicy: function(){
        $.ajax({
            url: "index.jsp?action=UpdateCheckAccessPolicy",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId, 'profileId': roomVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html) == "ProfileEjected"){
                    alert("You has been ejected to this room");
                    parent.history.back();
                }
            }
        }).success(jsRoom.refreshAccessPolicy);                
    },
    
    /**Check if the room has benn removed*/
    reloadIfExistPrivateRoom: function(){
        $.ajax({
            url: "index.jsp?action=UpdateCheckIfExistPrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html) == "not_found"){
                    alert("This room has been removed");
                    parent.history.back();
                }
            }
        }).success(jsRoom.refreshIfExistPrivateRoom);                
    },
    
    reloadRejectedInvitations: function(){
        $.ajax({
            url: "index.jsp?action=UpdateRejectedInvitation",
            type: "post",
            dataType: "json",            
            data:  {"roomId" : roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {
                $.each(data, function(index, element) {
                    alert("El usuario: " + element.login + " rechazo la invitacion al chat");
                });
            }
        }).success(jsRoom.refreshRejectedInvitations);  
    },
    
    refreshMessage: function(){
        setTimeout(jsRoom.getMessageList, roomVar.RELOAD_TIME);
    },
    
    refreshParticipants: function(){
        setTimeout(jsRoom.getParticipantList, roomVar.RELOAD_TIME);
    },
    
    refreshAllLoguedParticipants: function(){
        setTimeout(jsRoom.getAllLoguedParticipantList, roomVar.RELOAD_TIME);
    },
    
    refreshAccessPolicy: function(){
        setTimeout(jsRoom.reloadAccessPolicy, roomVar.RELOAD_TIME);
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
            url: "index.jsp?action=NewPrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'profileId': roomVar.profileId, 'title': title, 'inviteEmail': inviteEmail},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(roomId) {
                //the call ajax response roomId
                window.open("http://localhost:8080/chat/index.jsp?action=Room&profileId="+roomVar.profileId+"&roomId="+roomId , 'PrivateChat, _blank');
            }
        });
    },
    
    inviteParticipant: function(){
        var participantList = $("input[name='inviteEmailRoom']").val();

        $.ajax({
            url: "index.jsp?action=InviteParticipant",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId, 'profileId': roomVar.profileId, 'participantList': participantList},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                console.log("Send invitations success.");
            }
        });
    },
        
    sendMessage: function(){
        var message = $("#sendMessage input[name='message']").val();
        
        $.ajax({
            url: "index.jsp?action=SendMessage",
            type: "post",
            dataType: "html",            
            data: {"roomId":roomVar.roomId, "profileId":roomVar.profileId, "roomType":roomVar.roomType, "profileType":roomVar.profileType, "message": message},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                if($.trim(html)){
                    var message_empty = $("#messages tbody #message_empty").length;
                    if(message_empty == 1){
                        $("#messages tbody tr:last").remove();
                    }
                    $("#messages tbody:last").append(html);
                }
            }
        });
    },
 
    deleteMessage: function(messageId){
        var parent = $("#messages tr#"+messageId);
        var id = messageId;

        $.ajax({
            url: "index.jsp?action=DeleteMessage",
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
            url: "index.jsp?action=DeletePrivateRoom",
            type: "post",
            dataType: "html",            
            data:  {'roomId': roomVar.roomId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                window.close();
            }
        });
    },
    
    leavePrivateRoom: function(){
        $.ajax({
            url: "index.jsp?action=LeaveGroup",
            type: "post",
            dataType: "html",            
            data:  {'userAccessId': roomVar.userAccessId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                window.close();
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
        var parent = $("#participants tr#"+profileId);
        
        $.ajax({
            url: "index.jsp?action=EjectUser",
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
    }
    
};

/*Estos metodos se ejecutan recursivamente. 
* Los ajax devuelven promesas (return $.ajax() el ajax retorna una promesa), y si el ajax terminó (promise.done()) 
* recien se ejeccuta el setTimeOut llamando recursivamente a la misma funcion.
* Necesariamente para cada refresh tengo que hacer un metodo aparte
* Ademas estos metodos tiene dependencia, necesitan q los primeros ajax se terminen para recien ejecutarse
* pj: $.ajax().success(refreshMessage)
* */