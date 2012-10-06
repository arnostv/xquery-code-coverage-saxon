module namespace modf1="my:/coverage/foo/modF1.xq";

import module namespace modf1hlp="my:/coverage/foo/modF1_helper.xq";


declare function modf1:hey() {
    ("modf1",modf1hlp:hey())
};