export function operateRecord(obj) {


    const time = new  Date().getTime();
    let xhr = new XMLHttpRequest();
    xhr.timeout = 5000;
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {//请求成功
            if (xhr.status ===  200) {//响应成功
            }
        }
    }
    xhr.ontimeout  = function (e) {
        // XMLHttpRequest 超时。在此做某事。
        console.error("超时");
        xhr.abort();
    };
    let url = "http://192.168.11.196:8088";
    if(obj.type=="event") {
        url = url+`/uba/behavior/record?act=${obj.type}&t=${time}&r=${encodeURIComponent(obj.referrer)}&url=${encodeURIComponent(obj.url)}&properties=${btoa(JSON.stringify(obj.properties))}`
    }  else {
        url = url+`/uba/behavior/record?act=${obj.type}&t=${time}&r=${encodeURIComponent(obj.referrer)}&url=${encodeURIComponent(obj.url || '')}`
    }
    xhr.open('get', url,  true);
    xhr.send(null);
}

