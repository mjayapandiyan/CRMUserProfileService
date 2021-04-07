 create sequence hibernate_sequence start with 1 increment by  1;
 create sequence report_id_sequence start with 1000 increment by  1;
 create sequence user_id_seq start with 1000 increment by  1;
 create sequence user_profile_id_seq start with 1000 increment by  1;
 
    
    create table address (
       address_id number(19,0) not null,
        city varchar2(255 char),
        country varchar2(255 char),
        landmark varchar2(255 char),
        pincode varchar2(255 char),
        state varchar2(255 char),
        primary key (address_id)
    );
 
    
    create table bank_info (
       bank_id number(19,0) not null,
        bank_name number(19,0) not null,
        branch varchar2(255 char),
        ifsc_code varchar2(255 char) not null,
        company_id number(19,0) not null,
        primary key (bank_id)
    );
 
    
    create table city (
       city_id number(19,0) not null,
        city_name varchar2(255 char) not null,
        state_id number(19,0),
        primary key (city_id)
    );
 
    
    create table company_info (
       company_id number(19,0) not null,
        company_name varchar2(255 char),
        gst_effective_date timestamp,
        gst_no varchar2(255 char),
        gst_percentage varchar2(255 char),
        registration_date timestamp,
        registration_no number(19,0),
        website varchar2(255 char),
        primary key (company_id)
    );
 
    
    create table config_param (
       config_id number(19,0) not null,
        created_at timestamp,
        created_by varchar2(255 char),
        modified_at timestamp,
        modified_by varchar2(255 char),
        name varchar2(255 char),
        status varchar2(255 char),
        value varchar2(255 char),
        role_id number(19,0),
        primary key (config_id)
    );
 
    
    create table country (
       country_id number(19,0) not null,
        country_code varchar2(255 char) not null,
        country_name varchar2(255 char) not null,
        phone_code number(19,0) not null,
        primary key (country_id)
    );
 
    
    create table invoice_details (
       invoice_id number(19,0) not null,
        company_id number(19,0) not null,
        company_name varchar2(255 char) not null,
        invoice_date timestamp,
        gst_amount double precision,
        gst_percentage varchar2(255 char),
        invoice_mount double precision not null,
        period_end_date timestamp,
        invoice_name varchar2(255 char) not null,
        invoice_no number(19,0) not null,
        period_start_date timestamp,
        invoice_type varchar2(255 char) not null,
        project_id number(19,0) not null,
        remarks varchar2(255 char),
        status varchar2(255 char) not null,
        primary key (invoice_id)
    );
 
    
    create table project_info (
       project_id number(19,0) not null,
        allocated_by number(19,0) not null,
        allocated_date timestamp,
        allocation_status varchar2(255 char),
        assigned_from varchar2(255 char),
        assigned_to varchar2(255 char),
        billing_amount_usd number(19,0),
        client_id number(19,0),
        client_name varchar2(255 char),
        contract_end_date timestamp,
        contract_signed_date timestamp,
        project_name varchar2(255 char),
        profile_id number(19,0) not null,
        primary key (project_id)
    );
 
    
    create table report_details (
       report_id number(19,0) not null,
        attachment blob,
        company_id number(19,0),
        generate_by varchar2(255 char),
        generated_date timestamp,
        project_id number(19,0),
        project_name varchar2(255 char),
        report_name varchar2(255 char),
        report_type varchar2(255 char),
        user_id number(19,0),
        primary key (report_id)
    );
 
    
    create table role_mst (
       role_id number(19,0) not null,
        created_by varchar2(255 char),
        created_date timestamp,
        modified_by varchar2(255 char),
        modified_date timestamp,
        role_desc varchar2(60 char) not null,
        primary key (role_id)
    );
 
    
    create table state (
       state_id number(19,0) not null,
        state_name varchar2(255 char) not null,
        country_id number(19,0),
        primary key (state_id)
    );
 
    
    create table timesheet_details (
       time_sheet_id number(19,0) not null,
        timesheet_date varchar2(255 char),
        leave_reason varchar2(255 char),
        leave_date varchar2(255 char),
        project_code number(19,0),
        project_name varchar2(255 char),
        user_id number(19,0),
        user_name varchar2(255 char),
        utilization_hours number(19,0),
        primary key (time_sheet_id)
    );
 
    
    create table user_details (
       user_id number(19,0) not null,
        access_code varchar2(255 char) not null,
        contact_no varchar2(255 char) not null,
        created_at timestamp,
        created_by varchar2(255 char),
        dob varchar2(255 char) not null,
        email_id varchar2(255 char) not null,
        filename varchar2(255 char),
        first_name varchar2(255 char) not null,
        gender varchar2(255 char) not null,
        user_image blob,
        last_name varchar2(255 char) not null,
        login_date varchar2(255 char),
        modified_by varchar2(255 char),
        password varchar2(255 char),
        qualification varchar2(255 char),
        role varchar2(255 char),
        role_id number(19,0) not null,
        status varchar2(255 char),
        title varchar2(255 char),
        updated_at timestamp,
        username varchar2(255 char),
        address_id number(19,0),
        profile_id number(19,0),
        primary key (user_id)
    );
 
    
    create table user_profile (
       profile_id number(19,0) not null,
        company_id number(19,0) not null,
        company_name varchar2(255 char),
        date_of_exit timestamp,
        director_id number(19,0) not null,
        director_name varchar2(255 char) not null,
        is_terminated varchar2(255 char),
        is_termination_letter_issued varchar2(255 char),
        remarks varchar2(255 char),
        termination_initiated_by varchar2(255 char),
        termination_initiated_date timestamp,
        url varchar2(255 char),
        user_id number(19,0) not null,
        primary key (profile_id)
    );
 
    
    alter table user_details 
       add constraint UK_91tny9lfbnmj4o3xqiupwlots unique (login_date);
 
    
    alter table bank_info 
       add constraint FKme02hx647horrxxiagklscadx 
       foreign key (company_id) 
       references company_info;
 
    
    alter table city 
       add constraint FK6p2u50v8fg2y0js6djc6xanit 
       foreign key (state_id) 
       references state;
 
    
    alter table config_param 
       add constraint FK7ruo08ka9fptgr1tj9yh0lufg 
       foreign key (role_id) 
       references role_mst;
 
    
    alter table project_info 
       add constraint FKmso819o8l5giewvqpsly6yw28 
       foreign key (profile_id) 
       references user_profile;
 
    
    alter table state 
       add constraint FKghic7mqjt6qb9vq7up7awu0er 
       foreign key (country_id) 
       references country;
 
    
    alter table user_details 
       add constraint FK2j3d435pe9j2ajtoxfgpcj4i 
       foreign key (address_id) 
       references address;
 
    
    alter table user_details 
       add constraint FKjirt5efip3ktbjc80qtbur4j0 
       foreign key (profile_id) 
       references user_profile;