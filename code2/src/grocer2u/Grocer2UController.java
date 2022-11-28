package grocer2u;

import java.io.File;
import java.util.*;

public class Grocer2UController implements Controller
{
    	/* curstomerOrders
	輸入順序
優先事項
顧客姓名
板條箱按順序
地點
	 */

    /* serviceLocationMap

     */



    //


    static final String _myAccress = "Chad Valley" ; // 我的起點

    //
    //
    ArrayList< CustomerOrders > _customerOrders = new ArrayList< CustomerOrders >(); // 客戶清單列表
    ArrayList< ServiceLocationMap > _serviceLocationMap = new ArrayList< ServiceLocationMap >(); // 服務清單列表

    HashMap<String, Grocer2UNode > _node = new HashMap<>(); // 所有頂點的 HashMap
    // group
    List<CustomerOrdersGroup> _customerOrdersGroup = new ArrayList<CustomerOrdersGroup>();

    int _minCost ; // 最小成本

    int _runCost ; // 執行時的 cost

    boolean _DEBUG = false ; // 是否為 debug 模式


    // 建構函式
    public Grocer2UController()
    {

        // 載入 ServiceLocationMap
        loadServiceLocationMap();
        // 2. 載入 customerOrders
        loadCustomerOrders();
        // 3. 初始 CustomerOrdersGroup
        initCustomerOrdersGroup();



        //	out(showPathBetween( "Stourbridge" , "Smethwick" ));
        //	out(showShortestPathBetween( "Stourbridge" , "Smethwick" ));
        if( _DEBUG )
        {
            out( "\n\nlistOrdersInDeliveryTrip Test" );
            out( listOrdersInDeliveryTrip( 2 ));
            out( "\n\nshowPathForDeliveryTrip Test" );
            out( showPathForDeliveryTrip( 2 ));
        }
    }

    // 載入 ServiceLocationMap
    private void loadServiceLocationMap()
    {
        // 1. 載入 serviceLocationMap.csv
        try
        {

            // 1. 開檔 serviceLocationMap.csv
            File doc  = new File("serviceLocationMap.csv");
            Scanner obj = new Scanner(doc);

            // 2. 第一行(title)不要
            if( obj.hasNextLine())
                obj.nextLine();
            // 3. 判斷是否還有資料，讀到沒資料為止
            while (obj.hasNextLine())
            {
                // 4. 讀一行
                String str= obj.nextLine();
                // 5. 分解 keyCode ","
                String list[] = str.split( "," );
                // 6. 判斷內容長度是否符合資料格式
                if( list.length >= 3 )
                {
                    // 7. 建資料
                    ServiceLocationMap map = new ServiceLocationMap( str , list ) ;
                    // 8. 加到list _serviceLocationMap
                    _serviceLocationMap.add( map );
                    // 9. 加到頂點列表，設定為來源
                    addNode( map.FROM_LOCATION , map );
                    // 10. 加到頂點列表，設定為目地
                    addNode( map.TO_LOCATION , map );
                }

            }



        }catch( Exception e )
        {
            out( e.getMessage());
        }
    }

    //
     // 2. 載入 customerOrders
    private void loadCustomerOrders()
    {
        //
        try
        {
            // 2. 載入 customerOrders.csv
            //

            File doc  = new File("customerOrders.csv");
            Scanner obj = new Scanner(doc);

            // 第一行不要
            if( obj.hasNextLine() )
                obj.nextLine();
            // 讀到不能讀為止
            // 1. 讀入
            while (obj.hasNextLine())
            {
                String str= obj.nextLine();
                String list[] = str.split( "," );
                // 2. 建立 CustomerOrders
                if( list.length >= 5 )
                {
                    // 3. 放到 _customerOrders
                    _customerOrders.add( new CustomerOrders( str , list ));
                }

            }


        }catch( Exception e )
        {
            out( e.getMessage());
        }
    }
    //
    // 初始 CustomerOrdersGroup
    void initCustomerOrdersGroup()
    {
        int k = 0 , deliveryTrip = 1 ;
        CustomerOrdersGroup group = null ;


        // 1. 排序 _customerOrders
        Collections.sort( _customerOrders , new Comparator<CustomerOrders>()
        {
            @Override
            public int compare( CustomerOrders o1 , CustomerOrders o2 )
            {
                return o1.compareTo( o2 );
            }
        });

        // 2. 設定送貨行程
        for( CustomerOrders customerOrders : _customerOrders )
        {
            // 3. 判斷重量是否 > 15
            if(( k + customerOrders.Crates_in_Order ) > 15 )
            {
                // 4. 換新的 group
                k = 0 ;
                deliveryTrip ++ ;
                group = null ;
            }
            if( group == null )
            {
                group = new CustomerOrdersGroup( deliveryTrip );
                _customerOrdersGroup.add( group );
            }

            // 5. customerOrders 設定 deliveryTrip
            customerOrders.setDeliveryTrip( deliveryTrip );
            // 6. customerOrders 加到 group
            group.addCustomerOrders( customerOrders );
            // 7. 更新 crates
            k += customerOrders.Crates_in_Order ;
        }

    }
    //
    // 線加到列表頂點
    void addNode( String str , ServiceLocationMap line )
    {

        Grocer2UNode node ;
        // 1. 有這個 key 的 Grocer2UNode
        if( _node.containsKey( str ))
        {
            node = _node.get( str );
        }else
        {
            // 2. 沒有就建一個，並加到 map 中
            node = new Grocer2UNode( str );
            _node.put( str , node );
        }

        // 3. 將路徑加到頂點裡面
        node.addLine( line );
    }

    // 簡單輸出到螢幕
    private void out(String info) {
        System.out.println(info);
    }

    // case 1 列出所有送貨行程訂單
    @Override
    public String listAllDeliveryTripOrders()
    {
        // 1. 建輸出用 StringBuffer
        StringBuffer buf = new StringBuffer();
        // 2. loop 找 CustomerOrdersGroup
        for( CustomerOrdersGroup group : _customerOrdersGroup )
        {
            // 3. 加到 輸出字串
            buf.append( group.toString() );
        }

        // 4. 回傳取得的字串
        return buf.toString();
    }

    // case 2 列出送貨行程中的訂單
    @Override
    public String listOrdersInDeliveryTrip( int deliveryTripID )
    {
        // 1. loop _customerOrdersGroup
        for( CustomerOrdersGroup group : _customerOrdersGroup )
            if(group.getDeliveryTripID() == deliveryTripID ) // 2. 如果有相同的
                return group.toString(); // 3. 呼叫 toString() 輸出
        // 4. 輸出錯誤
        return "not data " ;

    }

    // init
    // 移動前初始所有資料
    void Init()
    {
        _minCost = 0 ;
        // 1. 初始 _serviceLocationMap 的所有元件( initLine
        for( final ServiceLocationMap d : _serviceLocationMap )
            d.initLine();
        // 2. 初始 _customerOrders 的所有元件( initNode
        for( final CustomerOrders d : _customerOrders )
            d.initNode();
        // 3. 初始 _node 的所有元件( initLine
        for( final Grocer2UNode d : _node.values() )
            d.initLine();
    }

    // 取得所有經過的路徑(string)
    public String getPath( Grocer2UNode end )
    {
        int i ;
        StringBuffer buf = new StringBuffer();
        ArrayList< Grocer2UNode > run = new ArrayList<>();
        int cost = _minCost = end._cost ;
        // 1. 將所找到的點由後往前
        while( end != null )
        {
            run.add( 0 , end );
            end = end._from ;
        }

        // 2. 再反回來，印 ->
        for( i =0 ; i < run.size() ; ++i )
        {
            if( i > 0 )
                buf.append( " -> " );
            buf.append( run.get( i )._name );
        }
        // 3. 輸出 cost
        buf.append( "\n cost: " + cost );
        return buf.toString();

    }
    // case 3 顯示之間的路徑，不考慮距離，找到就好
    @Override
    public String showPathBetween( String locationA , String locationB )
    {
        int i , k ;
        ArrayList< Grocer2UNode > run = new ArrayList<>();

        // 1. 取得 from , to ，失敗就結束
        Grocer2UNode from = _node.get( locationA );
        Grocer2UNode to = _node.get( locationB );
        if( from == null )
            return "not find locationA: " + locationA ;
        if( to == null )
            return "not find locationB: " + locationB ;
        // 2. 初始化
        Init();
        // 3. 放第一個點
        run.add( from );
        from._cost = 0 ;
        // 4. loop BFS
        for( k = 0 ; k < run.size() ; ++k  )
        {
            //
            final Grocer2UNode d = run.get(k );
            // 5. loop 找目標
            for( i = 0 ; i < d._to.size() ; ++i )
            {
                String stName = d._to.get( i );
                final Grocer2UNode node = _node.get( stName );
                // 6. 若未走過
                if( node._cost < 0  )
                {
                    // 7. 更新目前的 cost
                    final ServiceLocationMap mm = d._line.get( i );
                    node._from = d ;
                    node._cost = d._cost + mm.ESTIMATED_DISTANCE ;
                    // 8.加到 task
                    run.add( node );
                    // 9. 若是終點
                    if( to == node )
                    {
                        // 10. 呼叫 getPath 離開
                        return getPath( to );
                    }
                }
            }
        }

        // 11. 找不到就噴錯
        return "not find to: " + locationB ;
        // return null;
    }

    // case 4 顯示最短路徑之間
    @Override
    public String showShortestPathBetween( String locationA , String locationB )
    {

        ArrayList< Grocer2UNode > run = new ArrayList<>();
        int i  ;
        // 1. 取得 from , to ，失敗就結束
        Grocer2UNode from = _node.get( locationA );
        Grocer2UNode to = _node.get( locationB );
        if( from == null )
            return "not find locationA: " + locationA ;
        if( to == null )
            return "not find locationB: " + locationB ;
        // 2. 初始化
        Init();

        // 3. 放第一個點
        run.add( from );
        from._cost = 0 ;

        // 4. loop BFS
        while( run.size() > 0 )
        {
            // 5. 取點後就移除
            final Grocer2UNode d = run.get( 0 );
            run.remove( d );
            // 6. loop (目標)
            for( i = 0 ; i < d._to.size() ; ++i )
            {
                // 7. 取目標點
                String stName = d._to.get( i );
                final Grocer2UNode node = _node.get( stName );
                final ServiceLocationMap mm = d._line.get( i );
                // 8.並計算移動後的 cost
                final int cost = d._cost + mm.ESTIMATED_DISTANCE ;
                // 9. 判斷是否未走過，或是已走過，但 cost 比目前的小
                if(( node._cost < 0  )||( node._cost > ( cost )))// 最短的
                {
                    // 10. 更新資訊後，加到 task
                    node._from = d ;
                    node._cost = cost ;
                    run.add( node );

                }
            }
        }

        // 11. 取路徑
        return getPath( to );
    }

    // case 5 顯示送貨路線
    @Override
    public String showPathForDeliveryTrip( int deliveryTripID )
    {
        int i ;
        int runCost ;
        StringBuffer buf = new StringBuffer();
        ArrayList< Grocer2UNode > run = new ArrayList<>();
        ArrayList< Grocer2UNode > outBuf = new ArrayList<>();
        // 1. 本地，沒找到就 return
        Grocer2UNode myNode =  _node.get( _myAccress );
        if( myNode == null )
        {
            return "no my accress: " + _myAccress ;
        }


        // 2. 找 deliveryTripID
        for( CustomerOrdersGroup group : _customerOrdersGroup )
            if(group.getDeliveryTripID() == deliveryTripID )
            {
                // 3. 有找到，將點加到列表
                Iterator<CustomerOrders> it = group.getIterator();
                while( it.hasNext())
                {
                    run.add( _node.get( it.next().Location ));
                }

                // 4. 將 deliveryTrip 資料加到輸出
                buf.append( group.toString());
                buf.append( "\n" );
                break ;
            }

        //
        // 5. 沒找到任何點 噴錯後 return
        if( run.isEmpty())
            return "not find any location" ;

        // 6. 建立暫存器
        Grocer2UNode[] tempBuf = new Grocer2UNode[run.size()]  ;
        _runCost = -1 ;

        // 7. 找路徑
        // 7a. 標準─沒有優化
        build( outBuf , run.toArray( tempBuf ) , myNode );
        // 7b .進階，有優化
    //    buildEx( outBuf , tempBuf , run.toArray( new Grocer2UNode[run.size()] ) , 0 , myNode );
        runCost = _runCost ;
        // 8. 將經過的頂點印出來
        buf.append( showShortestPathBetween( myNode._name , tempBuf[0]._name ));
        buf.append( "\n" );
        for( i = 1 ; i < outBuf.size(); ++i )
        {
            buf.append( showShortestPathBetween( tempBuf[i-1]._name , tempBuf[i]._name ));
            buf.append( "\n" );

        }
        buf.append( showShortestPathBetween( tempBuf[i-1]._name , myNode._name ));
        buf.append( "\n" );

        // 9. 印依序走的點
        buf.append( "\n" + "run accress: " );
        buf.append( _myAccress );
        for( i = 0 ; i < outBuf.size(); ++i )
        {
            buf.append( " -> " );
            buf.append( outBuf.get( i)._name );
        }
        buf.append( " -> " );
        buf.append( _myAccress );
        //
        buf.append( "\nCost: " + runCost );
        return buf.toString();
    }


    // build // 不排序
    int build( List<Grocer2UNode> outBuf ,  Grocer2UNode[]  tempBuf , Grocer2UNode myNode )
    {
        int i ;
        int retCost = 0 ;
        // 1. 放第一個點
        outBuf.add( tempBuf[0] );
        // 2. loop 要走的點
        for( i = 1 ; i < tempBuf.length ; ++i )
        {
            // 3. 呼叫 showShortestPathBetween 抓輸出的點的 case
            showShortestPathBetween( tempBuf[i-1]._name , tempBuf[i]._name );
            // 4. 加到輸出的
            outBuf.add( tempBuf[i] );
            // 5. 加 cost
            retCost += this._minCost ;

        }
        // 6. 加myAccress 到 第一點的 cost
        showShortestPathBetween( myNode._name , tempBuf[0]._name );
        retCost += this._minCost ;
        // 7. 加最後一點到 myAccress 的 cost
        showShortestPathBetween( myNode._name , tempBuf[tempBuf.length-1]._name );
        retCost += this._minCost ;

        // 8. 更新最後的 cost
        return _runCost = retCost ;
    }
    // 找路徑(遞迴，BFS
    int buildEx( ArrayList< Grocer2UNode > outBuf , Grocer2UNode[] tempBuf , Grocer2UNode[] nodeBuf , int index , Grocer2UNode myNode )
    {
        int i ;
        // 1. 判斷是否全部走完
        if( index >= nodeBuf.length )
        {
            int cost = 0 ;
            // 2. 呼叫 showShortestPathBetween 算 myAccress -> 第一點cost
            showShortestPathBetween( myNode._name , tempBuf[0]._name );
            cost += this._minCost ;
            // 3. 呼叫 showShortestPathBetween 算 最後一點 -> myAccress 長度
            showShortestPathBetween( myNode._name , tempBuf[nodeBuf.length-1]._name );
            cost += this._minCost ;

            // 4. loop 所有中間各點的 cost
            for( i = 1 ; i < index ; ++i )
            {
                // 5. 呼叫 showShortestPathBetween ，算二點之間的 cost
                showShortestPathBetween( tempBuf[i-1]._name , tempBuf[i]._name );
                // 6. 判斷累計後的 cost 是否大於於最小值
                cost += this._minCost ;
                // 7. 己走過且 cost 大於最大，離開
                if(( _runCost >= 0 )&&( _runCost <= cost ))
                    return 0 ;
            }

            if( _DEBUG )	out( "test out: " + cost );
            // 8. 更新目前的最小值與路徑
            _runCost = cost ;
            outBuf.clear();
            for( i = 0 ; i < nodeBuf.length ; ++i )
                outBuf.add( tempBuf[i] );
            // 9. 結束
            return 0 ;
        }

        //
        // 10. loop 還沒走的點
        for( i = 0 ; i < nodeBuf.length ; ++i )
            if( nodeBuf[i] != null ) // 11. 還沒走過
            {
                final Grocer2UNode n = nodeBuf[i] ;
                // 12. 關閉
                nodeBuf[i] = null ;
                // 13. 存到暫存區
                tempBuf[index] = n ;
                // 14. 呼叫遞迴再找
                buildEx( outBuf , tempBuf , nodeBuf , index + 1 , myNode );
                // 15. 開門
                nodeBuf[i] = n ;
            }
        return 0 ;
    }
}
