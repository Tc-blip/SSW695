
x = ['49','7','6','7000']
if len(x) == 0:
    print(0) 

res = 0
list = []
dict = {}
for i in x:
    list.append((i[0],i))

list = sorted(list)[::-1]

print(list)

ans = ''
for j in range(len(list)):
    ans += list[j][1]

print(ans)