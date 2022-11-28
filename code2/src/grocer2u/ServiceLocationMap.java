package grocer2u;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//
class ServiceLocationMap
{
    String _src ; // 來源資料
    String FROM_LOCATION ; // 來源位置
    String TO_LOCATION ; // 目標位置
    int ESTIMATED_DISTANCE ; // 預估距離

    // cost
    int _cost ; // 成本

    public ServiceLocationMap( String src , String data[] )
    {
        _src = src;
        FROM_LOCATION = data[0] ;
        TO_LOCATION = data[1] ;
        ESTIMATED_DISTANCE = Integer.parseInt( data[2] );
    }


    // 初始成本
    public void initLine()
    {
        _cost = -1 ;
    }


}