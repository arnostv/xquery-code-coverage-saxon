import module namespace sub1="my:/coverage/sub1.xq";

declare function local:message($message) {
    <message>
        {$message}
        {sub1:hello("Joe")}
    </message>
};

declare function local:hello-world() {
    local:message("Hello world")
};

local:hello-world()