--
-- Created by IntelliJ IDEA.
-- User: wenhuang
-- Date: 2/14/2020
-- Time: 1:35 PM
-- To change this template use File | Settings | File Templates.
--

function fact (n)
    if n== 0 then
        return 1
    else
        return n*fact(n-1)
    end
end

print("enter a number")
a= io.read("*number")

print(fact(a))
