module namespace sub1="my:/coverage/sub1.xq";

import module namespace modb1="my:/coverage/bar/modB1.xq";
import module namespace modb2="my:/coverage/bar/modB2.xq";
import module namespace modf1="my:/coverage/foo/modF1.xq";
import module namespace modf2="my:/coverage/foo/modF2.xq";


declare function sub1:hello($name) {
    <hello>{$name}
        <hey>{sub1:hey()}</hey>
    </hello>
};

declare function sub1:hey() {
  (
    modb1:hey(),
    modb2:hey(),
    modf1:hey(),modf2:hey()
    )
};