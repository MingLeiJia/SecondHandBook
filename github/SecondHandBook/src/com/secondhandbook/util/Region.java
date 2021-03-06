package com.secondhandbook.util;
/**
 * @author MingLei Jia
 */
public class Region {

	//省级选项值
    public static String[] province = new String[] 
    		{"北京","上海","江苏","浙江","安徽"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    public static String[][] city = new String[][] 
            {
                    { "东城区", "西城区", "海淀区",  "朝阳区","丰台区", "石景山区","门头沟区", "通州区",
                             "顺义区","房山区",  "大兴区", "昌平区", "怀柔区","平谷区",  "密云县","延庆县" },
                    { "黄浦区", "浦东新区", "徐汇区", "长宁区", "静安区","普陀区","虹口区","杨浦区","闵行区"
                            	,"宝山区","嘉定区","金山区","松江区","青浦区","奉贤区","崇明县"},
                    { "南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮安市", "盐城市",
                            "扬州市","镇江市","泰州市","宿迁市" },
                    { "杭州市", "宁波市", "温州市","绍兴市","湖州市","嘉兴市","金华市","衢州市","台州市","丽水市","舟山市"},
                    { "合肥市","芜湖市","蚌埠市","淮南市","马鞍山","淮北市","铜陵市","安庆市","黄山市","阜阳市","宿州市"
                    	,"滁州市","六安市","宣城市","池州市","亳州市"}
            };
    
    //县级选项值
    public static String[][][] county = new String[][][] 
            {
                    {   //北京
                    	{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},
                    	{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}   
                    },
                    {    //上海
                    	{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},
                    	{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"} 
                    },
                    {    //江苏
                        {"玄武区","秦淮区","鼓楼区","建邺区","栖霞区","雨花台区","江宁区","浦口区","六合区","溧水区","高淳区"},
                        {"崇安区","南长区","北塘区","锡山区","惠山区","滨湖区","江阴市","宜兴市"},
                        {"鼓楼区","云龙区","贾汪区","泉山区","铜山区","丰县","沛县","睢宁县","邳州市","新沂市"},
                        {"天宁区","钟楼区","新北区","武进区","金坛区","溧阳市"},
                        {"姑苏区","虎丘区","吴中区","相城区","吴江区","张家港市","昆山市","太仓市","常熟市"},
                        {"崇川区","港闸区","通州区","海安县","如东县","如皋市","海门市","启东市"},
                        {"连云区","海州区","赣榆区","东海县","灌云县","灌南县"},
                        {"清河区","清浦区","淮安区","淮阴区","涟水县","洪泽县","盱眙县","金湖县"},
                        {"亭湖区","盐都区","大丰区","响水县","滨海县","阜宁县","射阳县","建湖县","东台市"},
                        {"广陵区","邗江区","江都区","宝应县","仪征市","高邮市"},
                        {"京口区","润州区","丹徒区","丹阳市","扬中市","句容市"},
                        {"海陵区","高港区","姜堰区","兴化市","靖江市","泰兴市"},
                        {"宿城区","宿豫区","沭阳县","泗阳县","泗洪县"}
                    },
                    {    //浙江
                        {"上城区","下城区","江干区","拱墅区","西湖区","滨江区","余杭区","萧山区","富阳区","建德市","临安市","桐庐县","淳安县"}, 
                        {"海曙区","江东区","江北区","北仑区","镇海区","鄞州区","余姚市","慈溪市","奉化市","象山县","宁海县"}, 
                        {"鹿城区","龙湾区","瓯海区","洞头区","瑞安市","乐清市","永嘉县","平阳县","苍南县","文成县","泰顺县"},
                        {"越城区","柯桥区","上虞区","诸暨市","嵊州市","新昌县"},
                        {"吴兴区","南浔区","德清县","长兴县","安吉县"},
                        {"南湖区","秀洲区","海宁市","平湖市","桐乡市","嘉善县","海盐县"},
                        {"婺城区","金东区","兰溪市","东阳市","永康市","义乌市","武义县","浦江县","磐安县"},
                        {"柯城区","衢江区","江山市","常山县","开化县","龙游县"},
                        {"椒江区","黄岩区","路桥区","临海市","温岭市","玉环县","三门县","天台县","仙居县"},
                        {"莲都区","龙泉市","青田县","缙云县","遂昌县","松阳县","云和县","庆元县","景宁畲族自治县"},
                        {"定海区","普陀区","岱山县","嵊泗县"}                        
                    },
                    {
                    	{"瑶海区","庐阳区","蜀山区","包河区","肥东县","肥西县","长丰县","庐江县","巢湖市"},
                    	{"镜湖区","弋江区","鸠江区","三山区","无为县","芜湖县","繁昌县","南陵县"},
                    	{"龙子湖区","蚌山区","禹会区","淮上区","五河县","固镇县","怀远县"},
                    	{"大通区","田家庵区","谢家集区","八公山区","潘集区","凤台县"},
                    	{"花山区","雨山区","博望区","含山县","和县","当涂县"},
                    	{"相山区","杜集区","烈山区","濉溪县"},
                    	{"铜官山区","狮子山区","郊区","铜陵县"},
                    	{"迎江区","大观区","宜秀区","怀宁县","桐城市","枞阳县","潜山县","太湖县","宿松县","望江县","岳西县"},
                    	{"屯溪区","黄山区","徽州区","歙县","休宁县","黟县","祁门县"},
                    	{"颍州区","颍泉区","颍东区","颍上县","界首市","临泉县","阜南县","太和县"},
                    	{"埇桥区","萧县","砀山县","灵璧县","泗县"},
                    	{"琅琊区","南谯区","天长市","明光市","全椒县","来安县","凤阳县","定远县"},
                    	{"金安区","裕安区","寿县","霍邱县","霍山县","金寨县","舒城县"},
                    	{"宣州区","郎溪县","广德县","宁国市","泾县","绩溪县","旌德县"},
                    	{"贵池区","青阳县","石台县","东至县"},
                    	{"谯城区","蒙城县","涡阳县","利辛县"}                	
                    }
            };
}
