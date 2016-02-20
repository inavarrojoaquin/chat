var homeVar = {};

$(document).ready(function(){
    homeVar.RELOAD_TIME = 5000;
    homeVar.profileId = window.top.jsPresentation.getProfileId();
    
    jsHome.getPublicRoom();
    jsHome.getInvitationList();
    jsHome.reloadEnableUserAccessPolicy();
});

var jsHome = {
    
    getPublicRoom: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetPublicRoom",
            type: "post",
            dataType: "html",            
            data:  {'profileId':homeVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("publicRooms", html);
            }
        }).success(jsHome.refreshPublicRoom);
    },
    
    getInvitationList: function(){
        $.ajax({
            url: "/chat/index.jsp?action=GetInvitationList",
            type: "post",
            dataType: "html",            
            data:  {'profileId':homeVar.profileId, 'callFrom':'home'},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(html) {
                jUtils.showing("invitations", html);
            }
        }).success(jsHome.refreshInvitationList);
    },
    
    refreshPublicRoom: function(){
        setTimeout(jsHome.getPublicRoom, homeVar.RELOAD_TIME);
    },
    
    refreshInvitationList: function(){
        setTimeout(jsHome.getInvitationList, homeVar.RELOAD_TIME);
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
    
    openPublicRoom: function(url, roomName, roomId){
        window.top.jsPresentation.setTab(url, roomName, roomId);
    },
    
    /**Check if the enabled user again */
    reloadEnableUserAccessPolicy: function(){
        var deferred = $.Deferred();
        $.ajax({
            url: "/chat/index.jsp?action=UpdateCheckAccessPolicyEnable",
            type: "post",
            dataType: "html",            
            data:  {'profileId': homeVar.profileId},
            error: function(hr) {
                jUtils.showing("error", hr);
            },
            success: function(data) {   
                if(data != "[]"){
                    $.each(JSON.parse(data), function(index, element) {
                        $("#myModal .modal-body").text("You have been enabled again from room: " + element.roomName);
                        $("#myModal").modal("show");
                        $("#myModal button").click(function(){
                            jsHome.deleteEnabledUserAgain(element.id);
                        });
                    });
                    deferred.resolve();
                }else{
                    deferred.resolve();
                }
            }
        });
        deferred.done(jsHome.refreshEnableUserAccessPolicy);                
    },
    
    refreshEnableUserAccessPolicy: function(){
        setTimeout(jsHome.reloadEnableUserAccessPolicy, homeVar.RELOAD_TIME);
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
    }
    
};

