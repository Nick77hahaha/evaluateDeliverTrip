package grocer2u;

import java.util.ArrayList;

//
class Grocer2UNode
{
    public String _name ; // 名稱
    public int _cost ; // 成本
    //
    public ArrayList<ServiceLocationMap> _line = new ArrayList<ServiceLocationMap>() ; // 地點清單
    public ArrayList<String> _to = new ArrayList<String>() ; // 目地地
    //
    public Grocer2UNode _from ; // 從那裡來的

    // 建構函式
    Grocer2UNode( String name )
    {
        _name = name ;
    }

    // 這個頂點加到線裡
    public void addLine( ServiceLocationMap line )
    {
        _line.add( line );
        if( _name.compareTo( line.FROM_LOCATION ) == 0 )
            _to.add( line.TO_LOCATION );
        else
            _to.add( line.FROM_LOCATION );
    }

    // 移動前初始化
    public void initLine()
    {
        _cost = -1 ;
        _from = null ;
    }

};