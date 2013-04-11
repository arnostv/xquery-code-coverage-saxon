module namespace sample1="my:/coverage/sample1.xq";

import module namespace sub1="my:/coverage/sub1.xq";

declare default function namespace "my:/coverage/sample1.xq";

declare function message($message) {
    <message>
        {$message}
        {sub1:hello(name())}
    </message>
};

declare function hello-world() {
    let $heme := fn:doc('file:src/main/xquery/input.xml')
    let $wrapped := heywrap(<towrap>{$heme}</towrap>)
    return ($wrapped, message("And the message"))

    (:return $wrapped :)
};

declare function heywrap($z) {
    let $foo := $z/message
    let $oyk := houwrap(<HOU>{$foo}</HOU>)
    return <heyx>{$oyk}</heyx>
};

declare function houwrap($q) {
    let $fooh := $q
    let $messageUpped := fn:upper-case($fooh/message/text())

    return if (fn:concat($messageUpped,'X') = 'HELLO WORLDX')
        then
            <hou a="{fn:upper-case('abc')}" t="{fn:current-time()}" up="{$messageUpped}" >{$fooh/message}</hou>
        else
            <oops>{$messageUpped}</oops>

};

declare function name() {
    "Joe"
};
