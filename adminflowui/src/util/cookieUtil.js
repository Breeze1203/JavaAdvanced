export function getCookie(name){
    //  按冒号分割cookie;
    let cookies = document.cookie.split(";");
    for (let i = 0; i < cookies.length; i++) {
        let strings = cookies[i].trim().split("=");
        if (strings[0] === name) {
            return strings[1];
        }
    }
}



