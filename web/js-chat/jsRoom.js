var roomVar = {};

$(document).ready(function(){
    roomVar.RELOAD_TIME = 15000;
    roomVar.roomId = $("input[name='roomId']").val();
    roomVar.roomType = $("input[name='roomType']").val();
    roomVar.roomOwner = $("input[name='roomOwner']").val();
    roomVar.privateRoom = roomVar.roomType == "private" ? true : false;
    roomVar.profileType = $("input[name='profileType']").val();
    roomVar.profileId = $("input[name='profileId']").val();
    roomVar.userAccessId = $("input[name='userAccessId']").val();
    
    // null y undefined evaluan en falso en js
    // !! transforma en boolean
    if ( (!! $("input[name='accessDenied']").val()) == false ){
        jsRoom.getMessageList();
    }
    
    if(roomVar.privateRoom){
        jsRoom.getAllLoguedParticipantList();
        
        if(roomVar.roomOwner == roomVar.profileId){
            jsRoom.reloadRejectedInvitations();
        }
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
        }).success(jsRoom.getParticipantList);
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
                var cant = $("#participants li[id]").length;

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
        }).success(jsRoom.getPublicRoomByProfile);
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
        }).success(jsRoom.reloadAccessPolicy);
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
        }).success(jsRoom.refreshMessage);                
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
        }).success(jsRoom.reloadIfExistPrivateRoom);
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
                    roomVar.privateRoom ? window.close() : parent.history.back();
                }
            }
        }).success(jsRoom.refreshAllLoguedParticipants);                
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
                if(data != "[]"){
                    $.each(data, function(index, element) {
                        alert("El usuario: " + element.login + " rechazo la invitacion al chat");
                    });
                }
            }
        }).success(jsRoom.refreshRejectedInvitations);  
    },
    
    refreshMessage: function(){
        setTimeout(jsRoom.getMessageList, roomVar.RELOAD_TIME);
    },
    
    refreshAllLoguedParticipants: function(){
        setTimeout(jsRoom.getAllLoguedParticipantList, roomVar.RELOAD_TIME);
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
                $("input[name='titleRoom']").val("");
                $("input[name='inviteEmailRoom']").val("");
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
                $("input[name='inviteEmailRoom']").val("");
                alert("Send invitations success.");
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
        var parent = $("#participants li#"+profileId);
        
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