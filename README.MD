# Application structures
1. Common
>Structure 
```sh
└─vn
  └──com
    └── taptap
        └── functions : chứa các funtions như simulate, import, search trip
           └──model : chứa trip model
        └── store : có chức năng lưu trữ column oriented data
           └── arithmetic: các thuật toán tìm rowIndexes theo card,tìm direction/timestamp theo rowIndexes
           └── util: chứa các util cho việc save/load db metadata, tìm theo column
```
- Cấu trúc dữ liệu
> - Ánh xạ import file từ row data sang column data để tăng hiệu năng cho việc tìm kiếm và thống kê trên large data
> - Import được chuyển đổi thành nhiều file mỗi file tương ứng với một column, đồng thời hỗ trợ split thành nhiều page theo row size.
> Việc chia tách thành nhiều file có ưu điểm trong việc đọc file, file không quá lớn nên có thể chia thành nhiều threads để đọc file, giảm thời gian tải file lên hệ thống.
- Thuật toán
> - Để tìm direction(IN/OUT), timestamp cần tìm ra indexs của cardId được lưu trữ trên column CARD_ID (col1), các index giống nhau trên các column khác nhau đều thuộc cùng một đối tượng, tức đều cùng một event;
> - VD: col0(index0),col1(index0),col2(index0),col3(index0),col4(index0) tạo thành event khi khách hàng lên/xuống BUS.
> - Áp dụng vào ứng dụng:
> > - Bước 1: duyệt trên column card_id (col1) để tìm ra các indexes match với card id của khách hàng
> > - Bước 2: duyệt trên indexs để tìm direction data
> > - Bước 3: duyệt trên indexs để tìm timestamp data
> > - Bước 4: duyệt trên direction data để tìm ra hành trình

- Ưu điểm
> - Không cần tải hết tất cả các file lên memory rồi tìm kiếm và thống kê
> - Để tìm value trong một column chỉ cần tải file chứa tất cả value của column đó lên
> - Hỗ trợ xử lý song song, nhiều theads cùng đọc một column, mỗi thread đọc 1 pageId khác nhau
> - Hiệu năng cao khi query trên large data, mất khoảng 1,5s để query 2 triệu record mà test trên Mac M1(8GB RAM,256GB SSD)
- Nhược điểm
> - Chậm trong thao tác insert/update. Lý do là là khi insert/update write trên nhiều file tương ứng mới column cần insert/update.
> Với row data thì chỉ cần write lên latest file.

- Sử dụng
> - Run vn.com.taptap.ImportDataApplication để simulate và import data, có thể thay đổi thay đổi total data import 
> - Run vn.com.taptap.Application để tìm kiếm hành trình
## TODO
- Quản lí thời gian import và indexes để có thể tìm đúng page id, không cần mở hết tất cả page
- Sử dụng caching để tăng hiệu năng tìm kiếm.