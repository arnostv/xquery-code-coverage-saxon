module namespace sub1="my:/coverage/sub1.xq";


declare function sub1:hello($name) {
    <hello>{$name}</hello>
};