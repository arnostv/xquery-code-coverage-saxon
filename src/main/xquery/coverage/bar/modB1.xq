module namespace modb1="my:/coverage/bar/modB1.xq";

declare function modb1:hey() {
    let $value := if (fn:true())
        then "modb1zzz"
        else "modb1yyy"
    return ($value)
};
