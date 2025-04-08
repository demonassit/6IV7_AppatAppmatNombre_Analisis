var mensaje = "habia una vez un patito que decia miua miau";
var password = "qwertyuiqwertyuiqwertyuiqwertyui";
var cifrado = CryptoJS.AES.encrypt(mensaje, password);
var descifrado = CryptoJS.AES.decrypt(cifrado, password);

//para que se vea
document.getElementById("demo00").innerHTML = mensaje;
document.getElementById("demo01").innerHTML = cifrado;
document.getElementById("demo02").innerHTML = descifrado;
document.getElementById("demo03").innerHTML = descifrado.toString(
    CryptoJS.enc.Utf8);

